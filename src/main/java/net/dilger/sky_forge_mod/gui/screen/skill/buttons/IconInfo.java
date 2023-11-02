package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.resources.ResourceLocation;

public enum IconInfo {
    HEART(0,0),
    HEART_PLUS(0, 1),
    HEART_DOUBLE(0,2);


    private final int size = 16;
    private final int xTexStart;
    private final int yTexStart;
    private final ResourceLocation texture = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/buttons/icons.png");
    private IconInfo(int xIndex, int yIndex)
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
