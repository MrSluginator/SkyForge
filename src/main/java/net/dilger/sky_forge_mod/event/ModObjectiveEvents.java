package net.dilger.sky_forge_mod.event;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.skills.PlayerSkillData;
import net.dilger.sky_forge_mod.util.PlayerDataUtility;
import net.dilger.sky_forge_mod.util.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID)
// this needs to be registered in mod class as an event bus but ModEvents does not
public class ModObjectiveEvents {

    @SubscribeEvent
    public void offensiveObjectiveEvent(LivingDeathEvent event) {
        // might change this to be correlated to the damage you deal so that you can't just make a mob farm and hit them once
        if (event.getSource().getEntity() instanceof Player player) {
            PlayerSkillData data = PlayerDataUtility.getPlayerData(player);
            data.addOffensive_xp(10);
            player.sendSystemMessage(Component.literal("Offensive Score : " + data.getOffensive_xp() ));
        }
    }

    @SubscribeEvent
    public void defensiveObjectiveEvent(ShieldBlockEvent event) {
        if (event.getEntity() instanceof Player player) {

            PlayerSkillData data = PlayerDataUtility.getPlayerData(player);
            data.addDefensive_xp((long) (event.getBlockedDamage() * 10));
            player.sendSystemMessage(Component.literal("Defensive Score : " + data.getDefensive_xp()));
        }
    }

    @SubscribeEvent
    public void miningObjectiveEvent(BlockEvent.BreakEvent event) {
        if (event.getState().is(ModTags.Blocks.MINING_SKILL_OBJECTIVES)) {
            Player player = event.getPlayer();
            PlayerSkillData data = PlayerDataUtility.getPlayerData(player);
            data.addMining_xp(10);
            player.sendSystemMessage(Component.literal("Mining Score : " + data.getMining_xp() ));
        }

    }

    @SubscribeEvent
    public void farmingObjectiveEvent(BlockEvent.BreakEvent event) {
        // this is going to need some tweaking
        if (event.getState().is(ModTags.Blocks.FARMING_SKILL_OBJECTIVES)) {
            Player player = event.getPlayer();
            PlayerSkillData data = PlayerDataUtility.getPlayerData(player);
            data.addFarming_xp(10);
            player.sendSystemMessage(Component.literal("Farming Score : " + data.getFarming_xp()));
        }

    }

    @SubscribeEvent
    public void tradingObjectiveEvent(PlayerInteractEvent.EntityInteractSpecific event) {
        // needs some fixing lul
        // xp gained 2x everytime you open menu but not when u trade
        if (event.getTarget() instanceof Villager) {
            Player player = event.getEntity();
            PlayerSkillData data = PlayerDataUtility.getPlayerData(player);
            data.addTrading_xp(10);
            player.sendSystemMessage(Component.literal("Trading Score : " + data.getTrading_xp() ));
        }

    }

    @SubscribeEvent
    public void fishingObjectiveEvent(ItemFishedEvent event) {
        Player player = event.getEntity();
        PlayerSkillData data = PlayerDataUtility.getPlayerData(player);
        data.addFishing_xp(10);
        player.sendSystemMessage(Component.literal("Fishing Score : " + data.getFishing_xp() ));


    }

    @SubscribeEvent
    public void mobilityObjectiveEvent(BlockEvent.BreakEvent event) {
//        if (event.getState().is(ModTags.Blocks.MINING_SKILL_OBJECTIVES)) {
//            PlayerSkillData data = PlayerDataUtility.getPlayerData(event.getPlayer());
//            data.addMobility_xp(10);
//            event.getPlayer().sendSystemMessage(Component.literal("Mobility Score : " + data.getMobility_xp() ));
//        }

    }
}
