package net.dilger.sky_forge_mod.skill.common_buff;

import com.google.common.collect.Maps;
import net.dilger.sky_forge_mod.skill.Buff;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CommonBuffs {
    // contains all common buffs
    private static final Map<ResourceLocation, Buff<?>> BUFFS = Maps.newHashMap();
    public static final RegenBuff REGEN_I = register(new RegenBuff((byte) 1));
    public static final ResistanceBuff RESISTANCE_I = register(new ResistanceBuff((byte) 1));
    public static final FireResistanceBuff FIRE_RESISTANCE_I = register(new FireResistanceBuff((byte) 1));
    public static final TemplateBuff TEMPLATE = register(new TemplateBuff());
    public static <T extends Buff<?>> T register(T buff) {
        assert BUFFS != null;
        if (BUFFS.containsKey(buff.getPacketResourceLocation())) {
            throw new IllegalArgumentException("Duplicate buff id " + buff.getPacketResourceLocation());
        } else {
            BUFFS.put(buff.getPacketResourceLocation(), buff);
            return buff;
        }
    }

    public static Buff getBuff(ResourceLocation pId) {

        if (!BUFFS.containsKey(pId)) {
            System.out.println("buffs does not contain : " + pId);
            System.out.println(BUFFS.keySet());
        }
        return BUFFS.get(pId);
    }

    public static Iterable<? extends Buff<?>> all() {
        return BUFFS.values();
    }
}
