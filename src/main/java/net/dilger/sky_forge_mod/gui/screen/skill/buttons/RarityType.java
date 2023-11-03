package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

public enum RarityType {
    COMMON(1),
    UNCOMMON(0),
    RARE(2),
    EPIC(3);
    private final int yIndex;
    private RarityType(int yIndex) {
        this.yIndex = yIndex;
    }

    public int getYIndex() {
        return yIndex;
    }
}
