package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.resources.ResourceLocation;

public enum SkillTreeIconType {

    OFFENSE(0,0),
    DEFENSE(0, 1),
    MINING(0,2),
    FARMING(0,2),
    TRADING(0,2),
    FISHING(0,2),
    MOBILITY(0,2);


    private final int size = 16;
    private final int xTexStart;
    private final int yTexStart;
    private final ResourceLocation texture = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/skill_tree_icons.png");
    private SkillTreeIconType(int xIndex, int yIndex)
    {
        this.xTexStart = xIndex * size;
        this.yTexStart = yIndex * size;

    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getSize() {
        return size;
    }

    public int getXTexStart() {
        return xTexStart;
    }

    public int getYTexStart() {
        return yTexStart;
    }
}
