package net.dilger.sky_forge_mod.client;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.networking.ModMessages;
import net.dilger.sky_forge_mod.networking.packets.skillXp.*;
import net.dilger.sky_forge_mod.util.ModTags;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientObjectiveEvents {


    @Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents { }
    @Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void offensiveObjectiveEvent(LivingDeathEvent event) {
            // might change this to be correlated to the damage you deal so that you can't just make a mob farm and hit them once
            if (event.getSource().getEntity() instanceof Player player) {
                ModMessages.sentToServer(new GainOffenseXpC2SPacket());
            }
        }

        @SubscribeEvent
        public static void defensiveObjectiveEvent(ShieldBlockEvent event) {
            if (event.getEntity() instanceof Player player) {


//                ClientSkillXpData.addDefensive_xp((long) (event.getBlockedDamage() * 10));
                ModMessages.sentToServer(new GainDefenseXpC2SPacket());
            }
        }

        @SubscribeEvent
        public static void miningObjectiveEvent(BlockEvent.BreakEvent event) {
            if (event.getState().is(ModTags.Blocks.MINING_SKILL_OBJECTIVES)) {
                Player player = event.getPlayer();
                ModMessages.sentToServer(new GainMiningXpC2SPacket());
            }

        }

        @SubscribeEvent
        public static void farmingObjectiveEvent(BlockEvent.BreakEvent event) {
            // this is going to need some tweaking
            if (event.getState().is(ModTags.Blocks.FARMING_SKILL_OBJECTIVES)) {
                Player player = event.getPlayer();
                ModMessages.sentToServer(new GainFarmingXpC2SPacket());
            }

        }

        @SubscribeEvent
        public static void tradingObjectiveEvent(PlayerInteractEvent.EntityInteractSpecific event) {
            // needs some fixing lul
            // xp gained 2x everytime you open menu but not when u trade
            if (event.getTarget() instanceof Villager) {
                Player player = event.getEntity();
                ModMessages.sentToServer(new GainTradingXpC2SPacket());
            }

        }

        @SubscribeEvent
        public static void fishingObjectiveEvent(ItemFishedEvent event) {
            Player player = event.getEntity();
            ModMessages.sentToServer(new GainFishingXpC2SPacket());


        }

        @SubscribeEvent
        public static void mobilityObjectiveEvent(BlockEvent.BreakEvent event) {
//        if (event.getState().is(ModTags.Blocks.MINING_SKILL_OBJECTIVES)) {
//            ModMessages.sentToServer(new GainMiningXpC2SPacket(PlayerSkillXp.SKILL_TYPE.MOBILITY));
//        }

        }
    }
}
