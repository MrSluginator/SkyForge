package net.dilger.sky_forge_mod.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.item.ModItems;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.S2CSyncPerksDataPacket;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.S2CSyncSkillXpPacket;
import net.dilger.sky_forge_mod.skill.PerkList;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.dilger.sky_forge_mod.skill.player.PlayerPerkCapability;
import net.dilger.sky_forge_mod.skill.player.PlayerSkillXpCapability;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;



@Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == VillagerProfession.FARMER) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Level 1
           /* trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 2),
                    new ItemStack(ModItems.STRAWBERRY.get(), 12),
                    10, 8, 0.02f));

            // Level 2
            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.CORN.get(), 6),
                    5, 9, 0.035f));

            // Level 3
            trades.get(3).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.GOLD_INGOT, 8),
                    new ItemStack(ModItems.CORN_SEEDS.get(), 2),
                    2, 12, 0.075f));*/
        }

        if(event.getType() == VillagerProfession.LIBRARIAN) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            ItemStack enchantedBook = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(Enchantments.THORNS, 2));

            // Level 1
            trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 32),
                    enchantedBook,
                    2, 8, 0.02f));
        }

        /*if(event.getType() == ModVillagers.SOUND_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            *//*trades.get(1).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModBlocks.SOUND_BLOCK.get(), 1),
                    16, 8, 0.02f));*//*

            trades.get(2).add((pTrader, pRandom) -> new MerchantOffer(
                    new ItemStack(Items.EMERALD, 6),
                    new ItemStack(ModBlocks.SAPPHIRE_ORE.get(), 2),
                    5, 12, 0.02f));
        }*/
    }

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 12),
                new ItemStack(ModItems.SAPPHIRE_BOOTS.get(), 1),
                3, 2, 0.2f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 24),
                new ItemStack(ModItems.METAL_DETECTOR.get(), 1),
                2, 12, 0.15f));
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            // attach a new capability to the player if it isn't already there
            if (!event.getObject().getCapability(PlayerSkillXpCapability.PLAYER_SKILL_XP).isPresent()) {
                event.addCapability(new ResourceLocation(SkyForgeMod.MOD_ID, "xp"), new PlayerSkillXpCapability());

            }

            if (!event.getObject().getCapability(PlayerPerkCapability.PLAYER_PERKS).isPresent()) {
                event.addCapability(new ResourceLocation(SkyForgeMod.MOD_ID, "perks"), new PlayerPerkCapability());

            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {

        event.getOriginal().getCapability(PlayerSkillXpCapability.PLAYER_SKILL_XP).ifPresent(oldStore -> {
            event.getOriginal().getCapability(PlayerSkillXpCapability.PLAYER_SKILL_XP).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });

        event.getOriginal().getCapability(PlayerPerkCapability.PLAYER_PERKS).ifPresent(oldStore -> {
            event.getOriginal().getCapability(PlayerPerkCapability.PLAYER_PERKS).ifPresent(newStore -> {
                newStore.copyFrom(oldStore);
            });
        });

    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if (!event.getLevel().isClientSide()) {
            if (event.getEntity() instanceof ServerPlayer player) {

                player.getCapability(PlayerSkillXpCapability.PLAYER_SKILL_XP).ifPresent(skillXp -> {
                    for (SKILL_TYPE st: SKILL_TYPE.values()) {
                        PacketHandling.sentToPlayer(new S2CSyncSkillXpPacket(skillXp.getSkillsXpMap(), st), player);
                    }
                });

                player.getCapability(PlayerPerkCapability.PLAYER_PERKS).ifPresent(playerPerks -> {
                    PacketHandling.sentToPlayer(new S2CSyncPerksDataPacket(playerPerks.getPerks()), player);
//                    PerkList.updatePlayerPerks();
                });

                PerkList.init();

            }
        }
    }

}
