package net.dilger.sky_forge_mod.networking.packets.skillXp;

import net.dilger.sky_forge_mod.networking.ModMessages;
import net.dilger.sky_forge_mod.networking.packets.SkillXpDataSyncS2CPacket;
import net.dilger.sky_forge_mod.skill.PlayerSkillXpProvider;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class GainTradingXpC2SPacket {
    private static final String MESSAGE_GAIN_XP = "message.sky_forge_mod.gain_xp";
    private final SKILL_TYPE skill_type = SKILL_TYPE.TRADING;

    public GainTradingXpC2SPacket() {
        System.out.println("empty"+this);
    }

    public GainTradingXpC2SPacket(FriendlyByteBuf buf) {
        System.out.println("buf"+this);
    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            System.out.println("HANDLE"+this);
            ServerPlayer player = context.getSender();
//            ServerLevel level = player.serverLevel();

            // Notify the player that they have gained a perk
            assert player != null;
            player.sendSystemMessage(Component.translatable(MESSAGE_GAIN_XP).withStyle(ChatFormatting.DARK_GREEN));

            // Change the player skill xp
            player.getCapability(PlayerSkillXpProvider.PLAYER_SKILL_XP).ifPresent(skill_xp -> {
                skill_xp.addSkillXp(1, skill_type);
                player.sendSystemMessage(Component.literal("Current Xp " + skill_xp.getSkillXp(skill_type))
                        .withStyle(ChatFormatting.GOLD));
                ModMessages.sentToPlayer(new SkillXpDataSyncS2CPacket(skill_xp.getSkillsXpMap()), player);
            });

            // Check if the player has leveled up
        });
        return true;
    }
}
