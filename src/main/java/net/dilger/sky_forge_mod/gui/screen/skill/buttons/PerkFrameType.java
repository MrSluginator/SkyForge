package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.minecraft.network.chat.Component;

public enum PerkFrameType {
    SQUARE(0, "square"),
    EXCITED(1, "excited"),
    SHIELD(2, "shield"),
    DIAMOND(3, "diamond"),
    CREST(4, "crest");

    private static final int LOCKED_COLOUR = -1072689136;
    private static final int blockSize = 32;
    private final int xIndex;
    private final String name;


    PerkFrameType(int xIndex, String name)
    {
        this.xIndex = xIndex;
        this.name = name;
    }

    public int getSize() {
        return switch (this) {
            case SQUARE -> 24;
            case CREST -> 28;
            default -> 26;
        };
    }

    public int getXTexStart() {

        return xIndex * blockSize;
    }

    public int getYTexStart(RarityType rarity) {
        return rarity.getYIndex() * blockSize;
    }

    public Component getName() {
        return Component.literal(name);
    }

    public static PerkFrameType byName(String name) {
        for (PerkFrameType frame: values()) {
            if (frame.name.equals(name)) {
                return frame;
            }
        }

        throw new IllegalArgumentException("Unknown frame type '" + name + "'");
    }
    public static int getBlockSize() {
        return blockSize;
    }
    public int getOffset() {
        return (blockSize - getSize())/2;
    }
}
