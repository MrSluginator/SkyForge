package net.dilger.sky_forge_mod.client;

import net.dilger.sky_forge_mod.client.screen.ExampleScreen;
import net.dilger.sky_forge_mod.client.screen.ExampleMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;

public class ClientHooks {

    public static void openExampleScreen() {
        Minecraft.getInstance().setScreen(new ExampleScreen());
    }

    public static void openExampleMenuScreen(){

    }
}
