package net.dilger.sky_forge_mod.gui.screen.skill;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.SkillTreeIconType;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class SkillTreeTab {

    private static final ResourceLocation tabTexture = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/tab_base.png");
    public static final int TEXTURE_WIDTH = 20;
    public static final int TEXTURE_HEIGHT = 18;

    private final SkillTreeIconType iconInfo;
    private final SkillTreeScreen screen;
    private final SKILL_TYPE skill_type;
    private boolean selected;
    private int pX, pY;



    public SkillTreeTab(@Nullable SkillTreeScreen activeScreen, SKILL_TYPE skill_type, boolean selected) {
        this.screen = activeScreen;
        this.skill_type = skill_type;
        this.selected = selected;
        this.iconInfo = SkillTreeIconType.valueOf(skill_type.toString());
    }

    public void drawTab(GuiGraphics graphics, int pX, int pY) {
        // draw tab base
        this.pX = pX;
        this.pY = pY;

        graphics.blit(this.tabTexture,
                pX, pY,
                0, 0,
                TEXTURE_WIDTH, TEXTURE_HEIGHT);

        // draw tab icon
        graphics.blit(iconInfo.getTexture(),
                pX + (TEXTURE_WIDTH - iconInfo.getSize())/2,
                pY + (TEXTURE_HEIGHT - iconInfo.getSize())/2,
                iconInfo.getXTexStart(),
                iconInfo.getYTexStart(),
                iconInfo.getSize(),
                iconInfo.getSize());
        // make dark if unselected
        if (!selected) {
            graphics.fillGradient(pX, pY,
                    pX + TEXTURE_WIDTH,
                    pY + TEXTURE_HEIGHT,
                    -1072689136,
                    -804253680);
        }
    }

    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return pMouseX > (double)pX
                && pMouseX < (double)(pX + this.TEXTURE_WIDTH)
                && pMouseY > (double)pY
                && pMouseY < (double)(pY + this.TEXTURE_HEIGHT);
    }
    public void selected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public SkillTreeScreen getScreen() {
        return screen;
    }
    public SKILL_TYPE getSkill_type() {
        return skill_type;
    }

    public int getX() {
        return pX;
    }

    public int getY() {
        return pY;
    }
}
