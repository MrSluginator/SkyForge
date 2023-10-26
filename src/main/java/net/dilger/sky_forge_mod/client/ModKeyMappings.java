package net.dilger.sky_forge_mod.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

import java.util.List;

public final class ModKeyMappings {

    private ModKeyMappings() {

    }

    public static final KeyMapping keySkillScreen = new KeyMapping("key.skills", InputConstants.KEY_K, KeyMapping.CATEGORY_MISC);
    public static List<KeyMapping> keyMappings;
    public static void init() {
        registerKey(keySkillScreen);
    }

    private static void registerKey(KeyMapping key) {
        keyMappings.add(key);
    }

    public static List<KeyMapping> getKeys() {
        return keyMappings;
    }

}
