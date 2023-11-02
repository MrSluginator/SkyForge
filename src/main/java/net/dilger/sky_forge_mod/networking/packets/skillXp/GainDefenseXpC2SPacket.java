package net.dilger.sky_forge_mod.networking.packets.skillXp;

import net.dilger.sky_forge_mod.networking.ModMessages;
import net.dilger.sky_forge_mod.networking.packets.SkillXpDataSyncS2CPacket;
import net.dilger.sky_forge_mod.skill.PlayerSkillXp;
import net.dilger.sky_forge_mod.skill.PlayerSkillXpProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GainDefenseXpC2SPacket {
    private static final String MESSAGE_GAIN_XP = "message.sky_forge_mod.gain_xp";
    private final PlayerSkillXp.SKILL_TYPE skill_type = PlayerSkillXp.SKILL_TYPE.DEFENSE;

    public GainDefenseXpC2SPacket() {
    }
    public GainDefenseXpC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

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
                skill_xp.addSkillXp(1, skill_type);
                player.sendSystemMessage(Component.literal("Current Xp " + skill_xp.getSkillXp(skill_type))
                        .withStyle(ChatFormatting.GOLD));
                ModMessages.sentToPlayer(new SkillXpDataSyncS2CPacket(skill_xp.getSkillsXpMap()), player);

                // Check if the player has leveled up and or met xp requirements

            });


        });
        return true;
    }
}
