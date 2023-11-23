package net.dilger.sky_forge_mod.skill.common_buff;

public class CommonBuffs {
    // contains all common buffs
/*    public static final NothingBuff NOTHING = register(new NothingBuff());
    public static final TemplateBuff TEMPLATE = register(new TemplateBuff());
    private static final Map<ResourceLocation, Buff<?>> CRITERIA = Maps.newHashMap();
    public static <T extends Buff<?>> T register(T buff) {
        if (CRITERIA.containsKey(buff.getId())) {
            throw new IllegalArgumentException("Duplicate criterion id " + buff.getId());
        } else {
            CRITERIA.put(buff.getId(), buff);
            return buff;
        }
    }

    @Nullable
    public static <T extends BuffInstance> Buff<T> getCriterion(ResourceLocation pId) {
        return (Buff<T>)CRITERIA.get(pId);
    }

    public static Iterable<? extends Buff<?>> all() {
        return CRITERIA.values();
    }*/
}
