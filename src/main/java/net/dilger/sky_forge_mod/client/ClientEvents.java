package net.dilger.sky_forge_mod.client;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.keySkills.consumeClick()) {
                Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed Screen Key!"));
            }
        }

    }

    @Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents{
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.keySkills);
        }

        @SubscribeEvent
        public static void registerguiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("example", ExampleHudOverlay.HUD);
        }
    }
}
