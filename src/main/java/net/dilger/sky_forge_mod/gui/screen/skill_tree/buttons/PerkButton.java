package net.dilger.sky_forge_mod.gui.screen.skill_tree.buttons;

import net.dilger.sky_forge_mod.gui.screen.skill_tree.SkillTreeScreen;
import net.dilger.sky_forge_mod.skills.Perk;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/*
Perk buttons are the buttons on the skill tree screen
that the user can click to unlock a new perk of that skill

*/

public class PerkButton extends ImageButton {
    private static final ResourceLocation BUTTONS_RESOURCE_LOCATION = new ResourceLocation("textures/gui/skills/perk_buttons_base.png");
    private static final int WIDTH = 26;
    private static final int HEIGHT = 26;

    private final SkillTreeScreen skillTreeScreen;
    private final Minecraft minecraft;
    private final Perk perk;
    private final DisplayInfo display;

    public PerkButton(SkillTreeScreen skillTreeScreen, Minecraft minecraft, Perk perk, DisplayInfo display) {
        this(Mth.floor(display.getX() * 28.0F), Mth.floor(display.getY() * 27.0F), 0, 0,
                skillTreeScreen, minecraft, perk, display);
    }
    private PerkButton(int pX, int pY, int pXTexStart, int pYTexStart, SkillTreeScreen skillTreeScreen, Minecraft minecraft, Perk perk, DisplayInfo display) {
        super(pX, pY, WIDTH, HEIGHT, pXTexStart, pYTexStart, BUTTONS_RESOURCE_LOCATION, Button::onPress);
        this.skillTreeScreen = skillTreeScreen;
        this.minecraft = minecraft;
        this.perk = perk;
        this.display = display;
    }

    @Override
    public void onPress() {
        // Do button thing here
        if (perkUnlocked()) {
            // Check if this was part of an exclusive branch
                // Lock the other branch
        }
        super.onPress();
    }

    private boolean perkUnlocked() {
        return true;
    }

    /*

    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
    private static final int HEIGHT = 26;
    private static final int BOX_X = 0;
    private static final int BOX_WIDTH = 200;
    private static final int FRAME_WIDTH = 26;
    private static final int ICON_X = 8;
    private static final int ICON_Y = 5;
    private static final int ICON_WIDTH = 26;
    private static final int TITLE_PADDING_LEFT = 3;
    private static final int TITLE_PADDING_RIGHT = 5;
    private static final int TITLE_X = 32;
    private static final int TITLE_Y = 9;
    private static final int TITLE_MAX_WIDTH = 163;
    private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
    private final SkillTreeScreen skillTree;
    private final Perk perk;
    private final DisplayInfo display;
    private final FormattedCharSequence title;
    private final int width;
    private final List<FormattedCharSequence> description;
    private final Minecraft minecraft;
    @Nullable
    private PerkButton parent;
    private final List<PerkButton> children = Lists.newArrayList();
    @Nullable
    private AdvancementProgress progress;
    private final int x;
    private final int y;

    public PerkButton(int x, int y, int width, int height, int xTexStart, int yTexStart, ResourceLocation resourceLocation, OnPress onPress) {
        
        super(x, y, width, height, xTexStart, yTexStart, resourceLocation, onPress);
    }

    @Override
    public void onPress() {
        // check if perk meets requirements for being unlocked
        // add perk to player
        super.onPress();
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // render connection lines
        drawConnectivity(guiGraphics, x, y, false);
        // render itself
        super.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        // render children
        for (PerkButton button: children) {
            renderWidget(guiGraphics, mouseX, mouseY, partialTick);
        }
    }
    
    private static float getMaxWidth(StringSplitter pManager, List<FormattedText> pText) {
        return (float)pText.stream().mapToDouble(pManager::stringWidth).max().orElse(0.0D);
    }

    // finds optimal text placement
*/
/*    private List<FormattedText> findOptimalLines(Component pComponent, int pMaxWidth) {
        StringSplitter stringsplitter = this.minecraft.font.getSplitter();
        List<FormattedText> list = null;
        float f = Float.MAX_VALUE;

        for(int i : TEST_SPLIT_OFFSETS) {
            List<FormattedText> list1 = stringsplitter.splitLines(pComponent, pMaxWidth - i, Style.EMPTY);
            float f1 = Math.abs(getMaxWidth(stringsplitter, list1) - (float)pMaxWidth);
            if (f1 <= 10.0F) {
                return list1;
            }

            if (f1 < f) {
                f = f1;
                list = list1;
            }
        }

        return list;
    }*//*


    @Nullable
    private AdvancementWidget getFirstVisibleParent(Advancement pAdvancement) {
        do {
            pAdvancement = pAdvancement.getParent();
        } while(pAdvancement != null && pAdvancement.getDisplay() == null);

        return pAdvancement != null && pAdvancement.getDisplay() != null ? this.skillTree.getWidget(pAdvancement) : null;
    }

    public void drawConnectivity(GuiGraphics guiGraphics, int x, int y, boolean pDropShadow) {
        if (this.parent != null) {
            int i = x + this.parent.x + 13;
            int j = x + this.parent.x + 26 + 4;
            int k = y + this.parent.y + 13;
            int l = x + this.x + 13;
            int i1 = y + this.y + 13;
            int j1 = pDropShadow ? -16777216 : -1;
            if (pDropShadow) {
                guiGraphics.hLine(j, i, k - 1, j1);
                guiGraphics.hLine(j + 1, i, k, j1);
                guiGraphics.hLine(j, i, k + 1, j1);
                guiGraphics.hLine(l, j - 1, i1 - 1, j1);
                guiGraphics.hLine(l, j - 1, i1, j1);
                guiGraphics.hLine(l, j - 1, i1 + 1, j1);
                guiGraphics.vLine(j - 1, i1, k, j1);
                guiGraphics.vLine(j + 1, i1, k, j1);
            } else {
                guiGraphics.hLine(j, i, k, j1);
                guiGraphics.hLine(l, j, i1, j1);
                guiGraphics.vLine(j, i1, k, j1);
            }
        }

        for(PerkButton button : this.children) {
            button.drawConnectivity(guiGraphics, x, y, pDropShadow);
        }

    }

    public void draw(GuiGraphics guiGraphics, int x, int y) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            float f = this.progress == null ? 0.0F : this.progress.getPercent();
            AdvancementWidgetType advancementwidgettype;
            if (f >= 1.0F) {
                advancementwidgettype = AdvancementWidgetType.OBTAINED;
            } else {
                advancementwidgettype = AdvancementWidgetType.UNOBTAINED;
            }

            guiGraphics.blit(WIDGETS_LOCATION, x + this.x + 3, y + this.y, this.display.getFrame().getTexture(), 128 + advancementwidgettype.getIndex() * 26, 26, 26);
            guiGraphics.renderFakeItem(this.display.getIcon(), x + this.x + 8, y + this.y + 5);
        }

        for(PerkButton button : this.children) {
            button.draw(guiGraphics, x, y);
        }

    }

    public int getWidth() {
        return this.width;
    }

    public void setProgress(AdvancementProgress pProgress) {
        this.progress = pProgress;
    }

    public void addChild(AdvancementWidget pAdvancementWidget) {
        this.children.add(pAdvancementWidget);
    }

    public void drawHover(GuiGraphics guiGraphics, int x, int y, float pFade, int width, int height) {
        boolean flag = width + x + this.x + this.width + 26 >= this.skillTree.getScreen().width;
        String s = this.progress == null ? null : this.progress.getProgressText();
        int i = s == null ? 0 : this.minecraft.font.width(s);
        boolean flag1 = 113 - y - this.y - 26 <= 6 + this.description.size() * 9;
        float f = this.progress == null ? 0.0F : this.progress.getPercent();
        int j = Mth.floor(f * (float)this.width);
        AdvancementWidgetType advancementwidgettype;
        AdvancementWidgetType advancementwidgettype1;
        AdvancementWidgetType advancementwidgettype2;
        if (f >= 1.0F) {
            j = this.width / 2;
            advancementwidgettype = AdvancementWidgetType.OBTAINED;
            advancementwidgettype1 = AdvancementWidgetType.OBTAINED;
            advancementwidgettype2 = AdvancementWidgetType.OBTAINED;
        } else if (j < 2) {
            j = this.width / 2;
            advancementwidgettype = AdvancementWidgetType.UNOBTAINED;
            advancementwidgettype1 = AdvancementWidgetType.UNOBTAINED;
            advancementwidgettype2 = AdvancementWidgetType.UNOBTAINED;
        } else if (j > this.width - 2) {
            j = this.width / 2;
            advancementwidgettype = AdvancementWidgetType.OBTAINED;
            advancementwidgettype1 = AdvancementWidgetType.OBTAINED;
            advancementwidgettype2 = AdvancementWidgetType.UNOBTAINED;
        } else {
            advancementwidgettype = AdvancementWidgetType.OBTAINED;
            advancementwidgettype1 = AdvancementWidgetType.UNOBTAINED;
            advancementwidgettype2 = AdvancementWidgetType.UNOBTAINED;
        }

        int k = this.width - j;
        RenderSystem.enableBlend();
        int l = y + this.y;
        int i1;
        if (flag) {
            i1 = x + this.x - this.width + 26 + 6;
        } else {
            i1 = x + this.x;
        }

        int j1 = 32 + this.description.size() * 9;
        if (!this.description.isEmpty()) {
            if (flag1) {
                guiGraphics.blitNineSliced(WIDGETS_LOCATION, i1, l + 26 - j1, this.width, j1, 10, 200, 26, 0, 52);
            } else {
                guiGraphics.blitNineSliced(WIDGETS_LOCATION, i1, l, this.width, j1, 10, 200, 26, 0, 52);
            }
        }

        guiGraphics.blit(WIDGETS_LOCATION, i1, l, 0, advancementwidgettype.getIndex() * 26, j, 26);
        guiGraphics.blit(WIDGETS_LOCATION, i1 + j, l, 200 - k, advancementwidgettype1.getIndex() * 26, k, 26);
        guiGraphics.blit(WIDGETS_LOCATION, x + this.x + 3, y + this.y, this.display.getFrame().getTexture(), 128 + advancementwidgettype2.getIndex() * 26, 26, 26);
        if (flag) {
            guiGraphics.drawString(this.minecraft.font, this.title, i1 + 5, y + this.y + 9, -1);
            if (s != null) {
                guiGraphics.drawString(this.minecraft.font, s, x + this.x - i, y + this.y + 9, -1);
            }
        } else {
            guiGraphics.drawString(this.minecraft.font, this.title, x + this.x + 32, y + this.y + 9, -1);
            if (s != null) {
                guiGraphics.drawString(this.minecraft.font, s, x + this.x + this.width - i - 5, y + this.y + 9, -1);
            }
        }

        if (flag1) {
            for(int k1 = 0; k1 < this.description.size(); ++k1) {
                guiGraphics.drawString(this.minecraft.font, this.description.get(k1), i1 + 5, l + 26 - j1 + 7 + k1 * 9, -5592406, false);
            }
        } else {
            for(int l1 = 0; l1 < this.description.size(); ++l1) {
                guiGraphics.drawString(this.minecraft.font, this.description.get(l1), i1 + 5, y + this.y + 9 + 17 + l1 * 9, -5592406, false);
            }
        }

        guiGraphics.renderFakeItem(this.display.getIcon(), x + this.x + 8, y + this.y + 5);
    }

    public boolean isMouseOver(int x, int y, int pMouseX, int pMouseY) {
        if (!this.display.isHidden() || this.progress != null && this.progress.isDone()) {
            int i = x + this.x;
            int j = i + 26;
            int k = y + this.y;
            int l = k + 26;
            return pMouseX >= i && pMouseX <= j && pMouseY >= k && pMouseY <= l;
        } else {
            return false;
        }
    }

    public void attachToParent() {
        if (this.parent == null && this.advancement.getParent() != null) {
            this.parent = this.getFirstVisibleParent(this.advancement);
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }

    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
*/

}