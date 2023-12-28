package net.dilger.sky_forge_mod.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {

    public static final String CATEGORY = "key.category.sky_forge_mod.tutorial";
    public static final String KEY_OPEN_SKILLSCREEN = "key.category.sky_forge_mod.open_skillscreen";
    public static final String KEY_OPEN_TESTSCREEN = "key.category.sky_forge_mod.open_testscreen";
    public static final String KEY_OPEN_EDITOR = "key.category.sky_forge_mod.open_editor";

    public static final KeyMapping OPEN_SKILLSCREEN = new KeyMapping(KEY_OPEN_SKILLSCREEN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, CATEGORY);
    public static final KeyMapping OPEN_TESTSCREEN = new KeyMapping(KEY_OPEN_TESTSCREEN, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, CATEGORY);
    public static final KeyMapping OPEN_EDITOR = new KeyMapping(KEY_OPEN_EDITOR, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_BACKSLASH, CATEGORY);

    public static final String KEY_CATEGORY_SKYFORGE = "key.category.sky_forge_mod.sky_forge";
    public static final String KEY_SKILL_MENU = "key.sky_forge_mod.skill_menu";

    public static final KeyMapping keySkills = new KeyMapping(KEY_SKILL_MENU, InputConstants.KEY_K, KEY_CATEGORY_SKYFORGE);
    
}
