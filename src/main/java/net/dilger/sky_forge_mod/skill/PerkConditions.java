package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;

public class PerkConditions {
    private static final Map<ResourceLocation, PerkCondition> CONDITIONS = Maps.newHashMap();
    // register all condition classes here
    public static <T extends PerkCondition> T register(T condition) {
        if (CONDITIONS.containsKey(condition.getResourceLocation())) {
            throw new IllegalArgumentException("Duplicate criterion id " + condition.getResourceLocation());
        } else {
            CONDITIONS.put(condition.getResourceLocation(), condition);
            return condition;
        }
    }
    @Nullable
    public static PerkCondition getCriterion(ResourceLocation pId) {
        return CONDITIONS.get(pId);
    }

    public static Iterable<? extends PerkCondition> all() {
        return CONDITIONS.values();
    }
}
