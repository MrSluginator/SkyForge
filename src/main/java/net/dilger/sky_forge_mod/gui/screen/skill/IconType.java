package net.dilger.sky_forge_mod.gui.screen.skill;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum IconType {

    HEART("heart",0,0),
    HEART_PLUS("heart plus",0, 1),
    HEART_DOUBLE("heart double",0,2);

    private final int size = 16;
    private static final int blockSize = 32;
    private final String name;
    private final int xTexStart;
    private final int yTexStart;
    private final ResourceLocation iconTexture = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/icons.png");

    private IconType(String name, int xIndex, int yIndex)
    {
        this.name = name;
        this.xTexStart = xIndex * blockSize;
        this.yTexStart = yIndex * blockSize;
    }

    public void draw(GuiGraphics graphics, int pX, int pY, int baseSize) {
        // aligns the texture so that it will be in the center
        int offSet = (baseSize-size)/2;
        graphics.blit(iconTexture, pX + offSet, pY + offSet, xTexStart, yTexStart, size, size);
    }

    public Component getName() {
        return Component.literal(name);
    }

    public static IconType byName(String name) {
        for (IconType icon: values()) {
            if (icon.name.equals(name)) {
                return icon;
            }
        }

        throw new IllegalArgumentException("Unknown icon type '" + name + "'");
    }
}
