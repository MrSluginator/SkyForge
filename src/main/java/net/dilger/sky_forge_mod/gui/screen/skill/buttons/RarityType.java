package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.minecraft.network.chat.Component;

public enum RarityType {
    COMMON(1, "common"),
    UNCOMMON(0, "uncommon"),
    RARE(2, "rare"),
    EPIC(3, "epic");
    private final int yIndex;
    private final String name;

    private RarityType(int yIndex, String name) {
        this.yIndex = yIndex;
        this.name = name;
    }

    public int getYIndex() {
        return yIndex;
    }

    public Component getName() {
        return Component.literal(name);
    }

    public static RarityType byName(String name) {
        for (RarityType rarity: values()) {
            if (rarity.name.equals(name)) {
                return rarity;
            }
        }

        throw new IllegalArgumentException("Unknown rarity type '" + name + "'");
    }
}
