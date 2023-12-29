package net.dilger.sky_forge_mod.client;

import net.dilger.sky_forge_mod.gui.screen.skill.SkillScreen;
import net.minecraft.client.Minecraft;

public class ClientHooks {
    public static void openSkillScreen() {
        Minecraft.getInstance().setScreen(new SkillScreen());
    }

}
