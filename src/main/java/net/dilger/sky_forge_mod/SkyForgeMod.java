package net.dilger.sky_forge_mod;

import com.mojang.logging.LogUtils;
import net.dilger.sky_forge_mod.block.ModBlocks;
import net.dilger.sky_forge_mod.block.entity.ModBlockEntities;
import net.dilger.sky_forge_mod.client.ModKeyMappings;
import net.dilger.sky_forge_mod.entity.ModEntities;
import net.dilger.sky_forge_mod.entity.client.RhinoRenderer;
import net.dilger.sky_forge_mod.event.ModObjectiveEvents;
import net.dilger.sky_forge_mod.gui.screen.GemPolishingStationScreen;
import net.dilger.sky_forge_mod.gui.screen.ModMenuTypes;
import net.dilger.sky_forge_mod.item.ModCreativeModTabs;
import net.dilger.sky_forge_mod.item.ModItems;
import net.dilger.sky_forge_mod.loot.ModLootModifiers;
import net.dilger.sky_forge_mod.recipe.ModRecipes;
import net.dilger.sky_forge_mod.sound.ModSounds;
import net.dilger.sky_forge_mod.villager.ModVillagers;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SkyForgeMod.MOD_ID)
public class SkyForgeMod {
    public static final String MOD_ID = "sky_forge_mod";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SkyForgeMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModLootModifiers.register(modEventBus);
        ModVillagers.register(modEventBus);

        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);

        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        ModRecipes.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ModObjectiveEvents());
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.CATMINT.getId(), ModBlocks.POTTED_CATMINT);
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.SAPPHIRE);
            event.accept(ModItems.RAW_SAPPHIRE);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.RHINO.get(), RhinoRenderer::new);

            MenuScreens.register(ModMenuTypes.GEM_POLISHING_MENU.get(), GemPolishingStationScreen::new);

            ModKeyMappings.init();
        }

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {

            for (KeyMapping key: ModKeyMappings.getKeys()) {
                event.register(key);
            }
        }
    }
}
