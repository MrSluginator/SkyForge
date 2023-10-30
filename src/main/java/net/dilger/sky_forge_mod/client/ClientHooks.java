package net.dilger.sky_forge_mod.client;

import net.dilger.sky_forge_mod.client.screen.ExampleBlockScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {
    public static void openExampleBlockScreen(BlockPos pos) {
        Minecraft.getInstance().setScreen(new ExampleBlockScreen(pos));
    }
}
