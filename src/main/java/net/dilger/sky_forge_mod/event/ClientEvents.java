package net.dilger.sky_forge_mod.event;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.client.ClientHooks;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
//need to put keybindings here and in KeyBiinding class
public class ClientEvents {
    @Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if(KeyBinding.OPEN_SKILL_SCREEN.consumeClick()){
                ClientHooks.openSkillScreen();
            }
        }
    }

    @Mod.EventBusSubscriber(modid = SkyForgeMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.OPEN_SKILL_SCREEN);
            event.register(KeyBinding.OPEN_EDITOR);
        }
    }
}
