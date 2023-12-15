package net.dilger.sky_forge_mod.skill;

public enum SKILL_TYPE {
    OFFENSE("offense"),
    DEFENSE("defense"),
    MINING("mining"),
    FARMING("farming"),
    TRADING("trading"),
    FISHING("fishing"),
    MOBILITY("mobility");

    private final String name;
    SKILL_TYPE(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return super.toString();
    }

    public static SKILL_TYPE byName(String name) {
        for (SKILL_TYPE type: values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown frame type '" + name + "'");
    }

    public String getName() {
        return this.name;
    }
}
