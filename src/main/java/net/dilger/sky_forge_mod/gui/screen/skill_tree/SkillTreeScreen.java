package net.dilger.sky_forge_mod.gui.screen.skill_tree;

import net.dilger.sky_forge_mod.gui.screen.skill_tree.buttons.PerkButton;
import net.dilger.sky_forge_mod.skills.Perk;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class SkillTreeScreen extends Screen {

    private final Minecraft minecraft;
    private final DisplayInfo display;
    private final Object icon;
    private float fade; // fade is the alpha value of the background
    private SkillScreen parentScreen;
    private Perk rootPerk;
    private final PerkButton rootButton;

    public SkillTreeScreen(Minecraft minecraft, Perk rootPerk, DisplayInfo display) {
        super(display.getTitle());
        this.minecraft = minecraft;
        this.display = display;
        this.icon = display.getIcon();
        this.rootPerk = rootPerk;
        this.rootButton = new PerkButton(this, minecraft, rootPerk, this.display);
        this.addPerkButton(this.rootButton, rootPerk);
    }

    public Perk getRootPerk() {
        return this.rootPerk;
    }

    public void drawSkillTreeTab(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY, boolean pIsSelected) {
        /*
        draws Tab?
        isn't this done by the skill screen?
        this.format.draw(pGuiGraphics, pOffsetX, pOffsetY, pIsSelected, this.index);*/
    }

    public void drawIcon(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY) {
//        this.format.drawIcon(pGuiGraphics, pOffsetX, pOffsetY, this.index, this.icon);
    }

    public void drawContents(GuiGraphics guiGraphics, int x, int y) {
        /*
        move screen to be aligned with the parent
        if (!this.centered) {
            this.scrollX = (double)(117 - (this.maxX + this.minX) / 2);
            this.scrollY = (double)(56 - (this.maxY + this.minY) / 2);
            this.centered = true;
        }*/

        guiGraphics.enableScissor(x, y, x + 234, y + 113);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((float)x, (float)y, 0.0F);
        /*
        draws in the background
        uses a tile system

        ResourceLocation resourcelocation = Objects.requireNonNullElse(this.display.getBackground(), TextureManager.INTENTIONAL_MISSING_TEXTURE);
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);
        int k = i % 16;
        int l = j % 16;

        for(int i1 = -1; i1 <= 15; ++i1) {
            for(int j1 = -1; j1 <= 8; ++j1) {
                guiGraphics.blit(resourcelocation, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }*/

        /*
        draw in the connection lines

        this.root.drawConnectivity(guiGraphics, i, j, true);
        this.root.drawConnectivity(guiGraphics, i, j, false);
        this.root.draw(guiGraphics, i, j);*/
        guiGraphics.pose().popPose();
        guiGraphics.disableScissor();
    }

    public void drawTooltips(GuiGraphics guiGraphics, int pMouseX, int pMouseY, int pWidth, int pHeight) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, 0.0F, -200.0F);
        guiGraphics.fill(0, 0, 234, 113, Mth.floor(this.fade * 255.0F) << 24);
        boolean flag = false;
        /*
        i and j are really just int versions of scroll lulz

        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);*/
        /*
        checks all perks to see if the mouse is hovering

        if (pMouseX > 0 && pMouseX < 234 && pMouseY > 0 && pMouseY < 113) {
            for(PerkButton perkButton : this.perkButtons.values()) {
                if (perkButton.isMouseOver(i, j, pMouseX, pMouseY)) {
                    flag = true;
                    perkButton.drawHover(guiGraphics, i, j, this.fade, pWidth, pHeight);
                    break;
                }
            }
        }*/

        guiGraphics.pose().popPose();
        if (flag) {
            this.fade = Mth.clamp(this.fade + 0.02F, 0.0F, 0.3F);
        } else {
            this.fade = Mth.clamp(this.fade - 0.04F, 0.0F, 1.0F);
        }

    }

    public boolean isMouseOver(int pOffsetX, int pOffsetY, double pMouseX, double pMouseY) {
        // return this.format.isMouseOver(pOffsetX, pOffsetY, this.index, pMouseX, pMouseY);
        return false;
    }

    @Nullable
    public static SkillTreeScreen create(Minecraft pMinecraft, SkillScreen skillScreen, int skillTreeIndex, Perk rootPerk) {
//        used when making a new advancement root / skill tree screen
//        actually used for initialising a skill tree

        /*if (rootPerk.getDisplay() == null) {
            return null;
        } else {
            for(SkillTreeFormat skillTreeFormat : SkillTreeFormat.values()) {
                if ((skillTreeIndex % SkillTreeFormat.MAX_TABS) < skillTreeFormat.getMax()) {
                    return new SkillTreeScreen(pMinecraft, rootPerk, rootPerk.getDisplay());//pMinecraft, skillScreen, skillTreeFormat, skillTreeIndex % SkillTreeFormat.MAX_TABS, skillTreeIndex / SkillTreeFormat.MAX_TABS, rootPerk, rootPerk.getDisplay());
                }

                skillTreeIndex -= skillTreeFormat.getMax();
            }

            return null;
        }*/
        return null;
    }

    public void scroll(double pDragX, double pDragY) {
        /*if (this.maxX - this.minX > 234) {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, (double)(-(this.maxX - 234)), 0.0D);
        }

        if (this.maxY - this.minY > 113) {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, (double)(-(this.maxY - 113)), 0.0D);
        }*/

    }

    public void addPerk(Perk perk) {

//        add a perk lul

        /*if (perk.getDisplay() != null) {
            PerkButton perkButton = new PerkButton(this, this.minecraft, perk, perk.getDisplay());
            this.addPerkButton(perkButton, perk);
        }*/
    }

    private void addPerkButton(PerkButton perkButton, Perk perk) {
        /*
        add a perkButton to the parent

        this.perkButtons.put(perk, perkButton);
        int i = perkButton.getX();
        int j = i + 28;
        int k = perkButton.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);

        for(PerkButton button : this.perkButtons.values()) {
            button.attachToParent();
        }*/

    }

    @Nullable
    public PerkButton getWidget(Perk perk) {
        // pre simple just gotta implement into the perkButton class
        //return this.perkButtons.get(perk);

        return null;
    }

    public SkillScreen getParentScreen() {
        return this.parentScreen;
    }
}
