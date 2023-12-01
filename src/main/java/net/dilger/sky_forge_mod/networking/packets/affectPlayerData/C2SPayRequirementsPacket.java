package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.skill.PlayerSkillXpProvider;
import net.dilger.sky_forge_mod.skill.Requirement;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SPayRequirementsPacket {

    //this is what is getting encoded / decoded
    //add all xp variables here??
    private static final String MESSAGE_GAIN_XP = "message.sky_forge_mod.gain_xp";
    private final Requirement requirement;

    //add variables that you want to be passed to the constructor below
    public C2SPayRequirementsPacket(Requirement requirement){
        this.requirement = requirement;
    }
    public C2SPayRequirementsPacket(FriendlyByteBuf buffer) {
        this(new Requirement(
                SKILL_TYPE.valueOf(buffer.readUtf()),
                buffer.readInt(),
                buffer.readInt(),
                Item.byId(buffer.readInt()),
                buffer.readInt()
        ));
    }

    //you need one buffer.write per variable you are passing to the constructor
    public void encode(FriendlyByteBuf buffer) {

        buffer.writeUtf(this.requirement.getSkillType().name());
        buffer.writeInt(this.requirement.getSkillLevel());
        buffer.writeInt(this.requirement.getXpCost());
        buffer.writeInt(Item.getId(this.requirement.getItem()));
        buffer.writeInt(this.requirement.getItemCost());


    }

    //this is the logic for what we want to do when this message is built
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            ServerPlayer player = context.getSender();
//            ServerLevel level = player.serverLevel();

            // Notify the player that they have gained a perk
            assert player != null;

            // Change the player skill xp

            player.getCapability(PlayerSkillXpProvider.PLAYER_SKILL_XP).ifPresent(skill_xp -> {
                skill_xp.getSkillLevel(this.requirement.getSkillType());

                boolean requirementsMet = true;
                // check skill level

                if (skill_xp.getSkillLevel(this.requirement.getSkillType()) < this.requirement.getSkillLevel()) {
                    requirementsMet = false;
                    player.sendSystemMessage(Component.literal("skill level current:" + skill_xp.getSkillLevel(this.requirement.getSkillType())));
                }

                // check player xp
                if (player.experienceLevel < this.requirement.getXpCost()) {
                    requirementsMet = false;
                    player.sendSystemMessage(Component.literal("xp level"));

                }
                // check items
                if (player.getInventory().countItem(this.requirement.getItem()) < this.requirement.getItemCost()) {
                    requirementsMet = false;
                    player.sendSystemMessage(Component.literal("item count"));

                }

                if (requirementsMet) {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Perk unlocked"));

                    PacketHandling.sentToServer(new C2SRemovePlayerXpPacket((byte) this.requirement.getXpCost()));

                    final int itemCost = this.requirement.getItemCost();
                    // remove items
                    int itemsRemoved = 0;
                    while (itemsRemoved < itemCost) {
                        int slotWithItem = player.getInventory().findSlotMatchingItem(new ItemStack(this.requirement.getItem()));
                        int slotItemCount = player.getInventory().getItem(slotWithItem).getCount();
                        if (slotItemCount < (itemCost - itemsRemoved)) {
                            player.getInventory().removeItem(slotWithItem, slotItemCount);
                            itemsRemoved += slotItemCount;
                        } else {
                            player.getInventory().removeItem(slotWithItem, itemCost - itemsRemoved);
                            itemsRemoved += itemCost - itemsRemoved;
                        }
                    }
                }
                else {
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal("Requirement not met"));
                }
            });

        });
        return true;
    }

}
