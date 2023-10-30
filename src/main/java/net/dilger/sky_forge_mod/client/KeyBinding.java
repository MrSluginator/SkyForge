package net.dilger.sky_forge_mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class KeyBinding {
    public static final String KEY_CATEGORY_SKYFORGE = "key.category.sky_forge_mod.sky_forge";
    public static final String KEY_SKILL_MENU = "key.sky_forge_mod.skill_menu";

    public static final KeyMapping keySkills = new KeyMapping(KEY_SKILL_MENU, InputConstants.KEY_K, KEY_CATEGORY_SKYFORGE);
}
