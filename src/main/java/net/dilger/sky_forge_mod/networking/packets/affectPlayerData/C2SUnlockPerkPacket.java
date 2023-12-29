package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.skill.player.PlayerPerkCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.Arrays;
import java.util.function.Supplier;

public class C2SUnlockPerkPacket {

    private final byte talent;

    //ANY TIME AffectPlayerTalents() is called all of the below methods happen

    public C2SUnlockPerkPacket(byte talent) {
        this.talent = talent;
    }

    public C2SUnlockPerkPacket(FriendlyByteBuf buf) {
        this(buf.readByte());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeByte(this.talent);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            ServerPlayer player = context.getSender();
            player.sendSystemMessage(Component.literal("debuggggg").withStyle(ChatFormatting.GOLD));

            player.sendSystemMessage(Component.literal(String.valueOf(player.getCapability(PlayerPerkCapability.PLAYER_TALENTS).isPresent())));

            player.getCapability(PlayerPerkCapability.PLAYER_TALENTS).ifPresent(playerTalents -> {

                    playerTalents.addPerk(talent);
                    PacketHandling.sentToPlayer(new S2CSyncPerksDataPacket(playerTalents.getPerks()), player);
                    player.sendSystemMessage(Component.literal("Saved Talents:" + Arrays.toString(playerTalents.getPerks())).withStyle(ChatFormatting.GOLD));
            });
        });
        return true;
    }


}
