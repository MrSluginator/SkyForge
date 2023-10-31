package net.dilger.sky_forge_mod.client;

import net.dilger.sky_forge_mod.client.screen.ExampleScreen;
import net.minecraft.client.Minecraft;

public class ClientHooks {

    public static void openExampleScreen() {
        Minecraft.getInstance().setScreen(new ExampleScreen());
    }


}
