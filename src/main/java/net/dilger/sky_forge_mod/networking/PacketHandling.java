package net.dilger.sky_forge_mod.networking;

import net.dilger.sky_forge_mod.networking.packets.affectClientData.PerksDataSyncS2CPacket;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.AffectPlayerLevel;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.C2SAddSkillXpPacket;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.S2CSyncSkillXpPacket;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.UpdateTalents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.dilger.sky_forge_mod.SkyForgeMod.MOD_ID;

public class PacketHandling {

    private static int id = 0;
    private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MOD_ID, "messages"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();

    /**
     * register all packets here
     */
    public static void register() {

        //this is for sending the class xp packets to the server. the constructor is called in the client objective events file
        INSTANCE.messageBuilder(C2SAddSkillXpPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SAddSkillXpPacket::encode)
                .decoder(C2SAddSkillXpPacket::new)
                .consumerMainThread(C2SAddSkillXpPacket::handle)
                .add();

        INSTANCE.messageBuilder(S2CSyncSkillXpPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(S2CSyncSkillXpPacket::new)
                .encoder(S2CSyncSkillXpPacket::encode)
                .consumerMainThread(S2CSyncSkillXpPacket::handle)
                .add();

        // changing character attributes
        INSTANCE.messageBuilder(AffectPlayerLevel.class, 3, NetworkDirection.PLAY_TO_SERVER)
                .encoder(AffectPlayerLevel::encode)
                .decoder(AffectPlayerLevel::new)
                .consumerMainThread(AffectPlayerLevel::handle)
                .add();

        INSTANCE.messageBuilder(UpdateTalents.class, 4, NetworkDirection.PLAY_TO_SERVER)
                .encoder(UpdateTalents::encode)
                .decoder(UpdateTalents::new)
                .consumerMainThread(UpdateTalents::handle)
                .add();

        INSTANCE.messageBuilder(PerksDataSyncS2CPacket.class, 5, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PerksDataSyncS2CPacket::new)
                .encoder(PerksDataSyncS2CPacket::toBytes)
                .consumerMainThread(PerksDataSyncS2CPacket::handle)
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
