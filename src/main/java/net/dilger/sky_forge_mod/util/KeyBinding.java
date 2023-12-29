package net.dilger.sky_forge_mod.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {

    public static final String CATEGORY_SKYFORGE = "key.category.sky_forge_mod.sky_forge";
    public static final String KEY_OPEN_SKILL_SCREEN = "key.category.sky_forge_mod.open_skill_screen";
    public static final String KEY_OPEN_EDITOR = "key.category.sky_forge_mod.open_editor";

    public static final KeyMapping OPEN_SKILL_SCREEN = new KeyMapping(KEY_OPEN_SKILL_SCREEN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, CATEGORY_SKYFORGE);
    public static final KeyMapping OPEN_EDITOR = new KeyMapping(KEY_OPEN_EDITOR, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, CATEGORY_SKYFORGE);
}
