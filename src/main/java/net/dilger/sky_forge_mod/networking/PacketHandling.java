package net.dilger.sky_forge_mod.networking;

import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.*;
import net.dilger.sky_forge_mod.networking.packets.perkEffects.*;
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
        INSTANCE.messageBuilder(AffectPlayerLevel.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(AffectPlayerLevel::new)
                .encoder(AffectPlayerLevel::encode)
                .consumerMainThread(AffectPlayerLevel::handle)
                .add();
        INSTANCE.messageBuilder(C2SRemovePlayerXpPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SRemovePlayerXpPacket::new)
                .encoder(C2SRemovePlayerXpPacket::encode)
                .consumerMainThread(C2SRemovePlayerXpPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SCheckRequirementsPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SCheckRequirementsPacket::new)
                .encoder(C2SCheckRequirementsPacket::encode)
                .consumerMainThread(C2SCheckRequirementsPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SPayRequirementsPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SPayRequirementsPacket::new)
                .encoder(C2SPayRequirementsPacket::encode)
                .consumerMainThread(C2SPayRequirementsPacket::handle)
                .add();


        // perk effect packets
        INSTANCE.messageBuilder(C2SIncreasePlayerMaxHealthPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SIncreasePlayerMaxHealthPacket::new)
                .encoder(C2SIncreasePlayerMaxHealthPacket::encode)
                .consumerMainThread(C2SIncreasePlayerMaxHealthPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SIncreasePlayerSpeedPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SIncreasePlayerSpeedPacket::new)
                .encoder(C2SIncreasePlayerSpeedPacket::encode)
                .consumerMainThread(C2SIncreasePlayerSpeedPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SIncreasePlayerJumpHeightPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SIncreasePlayerJumpHeightPacket::new)
                .encoder(C2SIncreasePlayerJumpHeightPacket::encode)
                .consumerMainThread(C2SIncreasePlayerJumpHeightPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SIncreasePlayerHastePacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SIncreasePlayerHastePacket::new)
                .encoder(C2SIncreasePlayerHastePacket::encode)
                .consumerMainThread(C2SIncreasePlayerHastePacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SIncreasePlayerRegenerationPacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SIncreasePlayerRegenerationPacket::new)
                .encoder(C2SIncreasePlayerRegenerationPacket::encode)
                .consumerMainThread(C2SIncreasePlayerRegenerationPacket::handle)
                .add();
        INSTANCE.messageBuilder(C2SIncreasePlayerResistancePacket.class, id++, NetworkDirection.PLAY_TO_SERVER)
                .decoder(C2SIncreasePlayerResistancePacket::new)
                .encoder(C2SIncreasePlayerResistancePacket::encode)
                .consumerMainThread(C2SIncreasePlayerResistancePacket::handle)
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
