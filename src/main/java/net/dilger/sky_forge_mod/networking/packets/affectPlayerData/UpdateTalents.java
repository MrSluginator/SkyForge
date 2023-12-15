package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.affectClientData.PlayerPerksS2CPacket;
import net.dilger.sky_forge_mod.talents.PlayerTalentCapability;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import java.util.Arrays;
import java.util.function.Supplier;

public class UpdateTalents {

    private final byte talent;

    //ANY TIME AffectPlayerTalents() is called all of the below methods happen

    public UpdateTalents(byte talent) {
        this.talent = talent;
    }

    public UpdateTalents(FriendlyByteBuf buf) {
        this(buf.readByte());
    }

    public void toBytes(FriendlyByteBuf buf) {

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

            player.sendSystemMessage(Component.literal(String.valueOf(player.getCapability(PlayerTalentCapability.PLAYER_TALENTS).isPresent())));

            player.getCapability(PlayerTalentCapability.PLAYER_TALENTS).ifPresent(playerTalents -> {

                    playerTalents.addTalent(talent);
                    PacketHandling.sentToPlayer(new PlayerPerksS2CPacket(playerTalents.getTalents()), player);
                    player.sendSystemMessage(Component.literal("Saved Talents:" + Arrays.toString(playerTalents.getTalents())).withStyle(ChatFormatting.GOLD));
            });
        });
        return true;
    }


}
