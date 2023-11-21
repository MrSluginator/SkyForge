package net.dilger.sky_forge_mod.networking;

import net.dilger.sky_forge_mod.networking.packets.SkillXpDataSyncS2CPacket;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.dilger.sky_forge_mod.SkyForgeMod.MOD_ID;

public class PacketHandling {
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MOD_ID, "messages"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();

    public static void register() {

        //this is for sending the class xp packets to the server. the constructor is called in the client objective events file
        INSTANCE.messageBuilder(SendXPpacket.class, 1, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SendXPpacket::encode)
                .decoder(SendXPpacket::new)
                .consumerMainThread(SendXPpacket::handle)
                .add();

        INSTANCE.messageBuilder(SkillXpDataSyncS2CPacket.class, 2, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillXpDataSyncS2CPacket::new)
                .encoder(SkillXpDataSyncS2CPacket::toBytes)
                .consumerMainThread(SkillXpDataSyncS2CPacket::handle)
                .add();

        // changing character attributes
        INSTANCE.messageBuilder(AffectPlayerLevel.class, 3, NetworkDirection.PLAY_TO_SERVER)
                .decoder(AffectPlayerLevel::new)
                .encoder(AffectPlayerLevel::toBytes)
                .consumerMainThread(AffectPlayerLevel::handle)
                .add();

    }

    public static <MSG> void sentToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sentToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllClients(MSG message){
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
