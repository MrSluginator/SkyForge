package net.dilger.sky_forge_mod.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {

    public static final String CATEGORY = "key.category.skyrim.tutorial";
    public static final String KEY_OPEN_SKILLSCREEN = "key.category.skyrim.open_skillscreen";

    public static final KeyMapping OPEN_SKILLSCREEN = new KeyMapping(KEY_OPEN_SKILLSCREEN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, CATEGORY);
    
}
