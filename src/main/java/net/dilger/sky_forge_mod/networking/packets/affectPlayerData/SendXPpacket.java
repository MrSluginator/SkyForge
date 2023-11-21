package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.SkillXpDataSyncS2CPacket;
import net.dilger.sky_forge_mod.skills.PlayerSkillXp;
import net.dilger.sky_forge_mod.skills.PlayerSkillXpProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendXPpacket {

    //this is what is getting encoded / decoded
    //add all xp variables here??
    private static final String MESSAGE_GAIN_XP = "message.sky_forge_mod.gain_xp";
    private static PlayerSkillXp.SKILL_TYPE skill_type;

    private final String chosenSkill;
    private final byte amount;

    //add variables that you want to be passed to the constructor below
    public SendXPpacket(String skill, byte amount) {
        this.chosenSkill = skill;
        this.amount = amount;
    }
    public SendXPpacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readByte());
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    //you need one buffer.write per variable you are passing to the constructor
    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.chosenSkill);
        buffer.writeByte(this.amount);

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

            switch(chosenSkill){
                case "Defense":
                     skill_type = PlayerSkillXp.SKILL_TYPE.DEFENSE;
                case "Farming":
                    skill_type = PlayerSkillXp.SKILL_TYPE.FARMING;
                case "Fishing":
                    skill_type = PlayerSkillXp.SKILL_TYPE.FISHING;
                case "Mining":
                    skill_type = PlayerSkillXp.SKILL_TYPE.MINING;
                case "Mobility":
                    skill_type = PlayerSkillXp.SKILL_TYPE.MOBILITY;
                case "Offense":
                    skill_type = PlayerSkillXp.SKILL_TYPE.OFFENSE;
                case "Trading":
                    skill_type = PlayerSkillXp.SKILL_TYPE.TRADING;
            }

              // Change the player skill xp

            player.getCapability(PlayerSkillXpProvider.PLAYER_SKILL_XP).ifPresent(skill_xp -> {
            skill_xp.addSkillXp(amount, skill_type);

            player.sendSystemMessage(Component.literal("Current Xp " + skill_xp.getSkillXp(skill_type))
                    .withStyle(ChatFormatting.GOLD));
            PacketHandling.sentToPlayer(new SkillXpDataSyncS2CPacket(skill_xp.getSkillsXpMap()), player);
            });

            // Check if the player has leveled up and or met xp requirements


        });
        return true;
    }

}