package net.dilger.sky_forge_mod.networking;

import net.dilger.sky_forge_mod.networking.packets.ExC2SPacket;
import net.dilger.sky_forge_mod.networking.packets.SkillXpDataSyncS2CPacket;
import net.dilger.sky_forge_mod.networking.packets.skillXp.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import static net.dilger.sky_forge_mod.SkyForgeMod.MOD_ID;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetID = 0;
    private static int id() {
        return packetID++;
    };

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ExC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExC2SPacket::new)
                .encoder(ExC2SPacket::toBytes)
                .consumerMainThread(ExC2SPacket::handle)
                .add();


//        skill XP packets

        net.messageBuilder(GainOffenseXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainOffenseXpC2SPacket::new)
                .encoder(GainOffenseXpC2SPacket::toBytes)
                .consumerMainThread(GainOffenseXpC2SPacket::handle)
                .add();
        net.messageBuilder(GainDefenseXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainDefenseXpC2SPacket::new)
                .encoder(GainDefenseXpC2SPacket::toBytes)
                .consumerMainThread(GainDefenseXpC2SPacket::handle)
                .add();
        net.messageBuilder(GainMiningXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainMiningXpC2SPacket::new)
                .encoder(GainMiningXpC2SPacket::toBytes)
                .consumerMainThread(GainMiningXpC2SPacket::handle)
                .add();
        net.messageBuilder(GainFarmingXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainFarmingXpC2SPacket::new)
                .encoder(GainFarmingXpC2SPacket::toBytes)
                .consumerMainThread(GainFarmingXpC2SPacket::handle)
                .add();
        net.messageBuilder(GainTradingXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainTradingXpC2SPacket::new)
                .encoder(GainTradingXpC2SPacket::toBytes)
                .consumerMainThread(GainTradingXpC2SPacket::handle)
                .add();
        net.messageBuilder(GainFishingXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainFishingXpC2SPacket::new)
                .encoder(GainFishingXpC2SPacket::toBytes)
                .consumerMainThread(GainFishingXpC2SPacket::handle)
                .add();
        net.messageBuilder(GainMobilityXpC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GainMobilityXpC2SPacket::new)
                .encoder(GainMobilityXpC2SPacket::toBytes)
                .consumerMainThread(GainMobilityXpC2SPacket::handle)
                .add();

        net.messageBuilder(SkillXpDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SkillXpDataSyncS2CPacket::new)
                .encoder(SkillXpDataSyncS2CPacket::toBytes)
                .consumerMainThread(SkillXpDataSyncS2CPacket::handle)
                .add();

    }


    public static <MSG> void sentToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }
    public static <MSG> void sentToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
