package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

public enum PerkType {
    SQUARE(0),
    EXCITED(1),
    SHIELD(2),
    DIAMOND(3),
    CREST(4);

    private static final int LOCKED_COLOUR = -1072689136;
    private static final int blockSize = 32;
    private final int xIndex;


    PerkType(int xIndex)
    {
        this.xIndex = xIndex;
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

    public PerkType getTypeFromIndex(int index) {
        if (index == 0) return SQUARE;
        if (index == 1) return EXCITED;
        if (index == 2) return SHIELD;
        if (index == 3) return DIAMOND;
        if (index == 4) return CREST;

        return null;
    }

}
