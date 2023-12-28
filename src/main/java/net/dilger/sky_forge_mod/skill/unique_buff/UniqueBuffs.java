package net.dilger.sky_forge_mod.skill.unique_buff;

import com.google.common.collect.Maps;
import net.dilger.sky_forge_mod.skill.Buff;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class UniqueBuffs {
    // contains all common buffs
    private static final Map<ResourceLocation, Buff<?>> CRITERIA = Maps.newHashMap();
    public static <T extends Buff<?>> T register(T buff) {
        assert CRITERIA != null;
        if (CRITERIA.containsKey(buff.getPacketResourceLocation())) {
            throw new IllegalArgumentException("Duplicate criterion id " + buff.getPacketResourceLocation());
        } else {
            CRITERIA.put(buff.getPacketResourceLocation(), buff);
            return buff;
        }
    }

    public static Buff<?> getBuff(ResourceLocation pId) {
        return CRITERIA.get(pId);
    }

    public static Iterable<? extends Buff<?>> all() {
        return CRITERIA.values();
    }
}
