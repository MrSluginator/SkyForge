package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.IconType;
import net.dilger.sky_forge_mod.skill.Perk;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;

public class PerkButton extends ImageButton {

    private final Perk perk;
    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/buttons/perk_buttons_base.png");
    private final PerkType perkType;
    private final RarityType rarity;
    private final IconType icon;


    /*
    * screen position is denoted with prefix p
    * relative position is denoted with prefix r
    * texture is prefix t
    */
    public PerkButton(Perk perk, int pX, int pY, PerkType perkType, RarityType rarity, @Nullable IconType icon, OnPress onPress) {
        super(pX, pY,
                perkType.getSize(),
                perkType.getSize(),
                perkType.getXTexStart(),
                perkType.getYTexStart(rarity),
                0, // assumes that there is a hovered texture then a disabled texture off set by some Y
                BASE_TEXTURE,
                onPress);

        this.perk = perk;
        this.perkType = perkType;
        this.rarity = rarity;
        this.icon = icon;
    }

    public void updatePosition(int pX, int pY) {
        setX(pX);
        setY(pY);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        drawConnectivity(graphics);
        renderWidget(graphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(graphics, pMouseX, pMouseY, pPartialTick);
        if (icon != null) icon.draw(graphics, getX(), getY(), perkType.getSize());
    }

    public void drawConnectivity(GuiGraphics graphics) {
        Perk parent = perk.getParent();

        if (parent != null) {
            PerkButton parentButton = parent.getButton();

            int parentCenterX = parentButton.getX() + parentButton.width/2;
            int midwayX = parentButton.getX() + parentButton.width + 4;
            int parentCenterY = parentButton.getY() + parentButton.height/2;
            int childCenterX = getX() + width/2;
            int childCenterY = getY() + height/2;
            int colour = Color.WHITE.hashCode();
            /*
            if (pDropShadow) {
                pGuiGraphics.hLine(midwayX, parentCenterX, parentCenterY - 1, colour);
                pGuiGraphics.hLine(midwayX + 1, parentCenterX, parentCenterY, colour);
                pGuiGraphics.hLine(midwayX, parentCenterX, parentCenterY + 1, colour);
                pGuiGraphics.hLine(childCenterX, midwayX - 1, childCenterY - 1, colour);
                pGuiGraphics.hLine(childCenterX, midwayX - 1, childCenterY, colour);
                pGuiGraphics.hLine(childCenterX, midwayX - 1, childCenterY + 1, colour);
                pGuiGraphics.vLine(midwayX - 1, childCenterY, parentCenterY, colour);
                pGuiGraphics.vLine(midwayX + 1, childCenterY, parentCenterY, colour);*/

            // line coming out of parent
            graphics.hLine(parentCenterX, midwayX, parentCenterY, colour);
            // line going into child
            graphics.hLine(midwayX, childCenterX, childCenterY, colour);
            // vertical connection line
            graphics.vLine(midwayX, parentCenterY, childCenterY, colour);

        }

    }
}
