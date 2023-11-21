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
    private final PerkFrameType perkFrameType;
    private final RarityType rarity;
    private final IconType icon;



    // draws the button centered on the pX and pY
    public PerkButton(Perk perk, PerkFrameType perkFrameType, RarityType rarity, @Nullable IconType icon, OnPress onPress) {
        super(0, 0,
                perkFrameType.getSize(),
                perkFrameType.getSize(),
                perkFrameType.getXTexStart(),
                perkFrameType.getYTexStart(rarity),
                0, // assumes that there is a hovered texture then a disabled texture off set by some Y
                BASE_TEXTURE,
                onPress);

        this.perk = perk;
        this.perkFrameType = perkFrameType;
        this.rarity = rarity;
        this.icon = icon;
    }


    public void updatePosition(int pX, int pY) {
        // center texture on the positional location
        setX(pX + this.perkFrameType.getOffset());
        setY(pY + this.perkFrameType.getOffset());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(graphics, pMouseX, pMouseY, pPartialTick);
        if (icon != null) icon.draw(graphics, getX(), getY(), perkFrameType.getSize());
    }

    public void drawConnectivity(GuiGraphics graphics, boolean drawShadow) {
        Perk parent = perk.getParent();

        if (parent != null) {
            PerkButton parentButton = parent.getButton();

            int midwayX = (parentButton.getCenterX() + getCenterX())/2;
            int parentCenterX = parentButton.getCenterX();
            int parentCenterY = parentButton.getCenterY();
            int childCenterX = getCenterX();
            int childCenterY = getCenterY();
            int colour = Color.WHITE.hashCode();

            if (drawShadow) {
                graphics.hLine(midwayX, parentCenterX, parentCenterY - 1, colour);
                graphics.hLine(midwayX + 1, parentCenterX, parentCenterY, colour);
                graphics.hLine(midwayX, parentCenterX, parentCenterY + 1, colour);
                graphics.hLine(childCenterX, midwayX - 1, childCenterY - 1, colour);
                graphics.hLine(childCenterX, midwayX - 1, childCenterY, colour);
                graphics.hLine(childCenterX, midwayX - 1, childCenterY + 1, colour);
                graphics.vLine(midwayX - 1, childCenterY, parentCenterY, colour);
                graphics.vLine(midwayX + 1, childCenterY, parentCenterY, colour);
            }
            // line coming out of parent
            graphics.hLine(parentCenterX, midwayX, parentCenterY, colour);
            // line going into child
            graphics.hLine(midwayX, childCenterX, childCenterY, colour);
            // vertical connection line
            graphics.vLine(midwayX, parentCenterY, childCenterY, colour);

        }

    }

    public int getCenterX() {
        return getX() + this.width/2;
    }

    public int getCenterY() {
        return getY() + this.height/2;
    }

    public Perk getPerk() {
        return perk;
    }

    public PerkFrameType getPerkType() {
        return perkFrameType;
    }

    public RarityType getRarity() {
        return rarity;
    }

    public IconType getIcon() {
        return icon;
    }
}
