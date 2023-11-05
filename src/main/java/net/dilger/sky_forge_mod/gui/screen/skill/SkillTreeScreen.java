package net.dilger.sky_forge_mod.gui.screen.skill;

import com.google.common.collect.Sets;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.dilger.sky_forge_mod.skill.Perk;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.dilger.sky_forge_mod.skill.TreeNodePosition;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Map;
import java.util.Set;

public class SkillTreeScreen extends Screen {

    private static final Component TITLE =
            Component.translatable("gui." + SkyForgeMod.MOD_ID + ".skill_tree_screen");
    private final SkillTreeEditor editor;
    private final Perk root;
    //    private final PerkButton rootButton;
    private final int imageWidth, imageHeight;
    private final Map<SKILL_TYPE, SkillTreeTab> tabs;
    private final SKILL_TYPE skill_type;
    private int leftPos, topPos;
    private boolean isScrolling;
    private double scrollX;
    private double scrollY;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;

    public SkillTreeScreen(SKILL_TYPE skill_type, Map<SKILL_TYPE, SkillTreeTab> tabs) {
        super(TITLE);

        editor = new SkillTreeEditor(this);

        this.imageWidth = 176;
        this.imageHeight = 166;

        this.tabs = tabs;

        // testing
        // need to find a better way to add buttons to the skill screen
        this.skill_type = skill_type;
        this.root = new Perk(null, PerkType.SQUARE, RarityType.COMMON, null);

    }

    private void makeTree(Perk perk, int depth, int subTreeSize, RarityType rarity) {

            if (depth > 0) {
                for (int branch = 0; branch < subTreeSize; branch++) {
                    perk.addChild(new Perk(perk, PerkType.CREST.getTypeFromIndex(branch%5), rarity, null));
                }
                for (Perk child : perk.getChildren()) {
                    makeTree(child, depth - 1, Math.max(subTreeSize - 1, 0), rarity);
                }
            }

    }

    private void makeNewTree(Perk root, int depth, int subTreeSize, RarityType rarity) {
        Perk treeRoot = new Perk(root, PerkType.DIAMOND, rarity, null);
        root.addChild(treeRoot);

        makeTree(treeRoot, depth, subTreeSize, rarity);
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width) / 2;
        this.topPos = (this.height) / 2;

        if(this.minecraft == null) return;
        Level level = this.minecraft.level;
        if(level == null) return;

        // if the tab is not attached to anything then add this to it otherwise make it the selected tab
        // this should probably be in where the tab is selected but that's for later lol
        if (tabs.get(skill_type).getScreen() != this) {
            tabs.put(skill_type, new SkillTreeTab(this, skill_type, true));
        }
        else {
            if (!tabs.get(skill_type).isSelected()) {
                tabs.get(skill_type).selected(true);
            }
        }

        // position buttons
        TreeNodePosition.run(root);
        // add buttons to screen
        for (PerkButton perkButton: getAllButtons(root)) {
            System.out.println(perkButton.toString());
            addRenderableWidget(perkButton);
        }
        editor.updateChildren();

    }

    public void addButton(Button button) {
        TreeNodePosition.run(root);
        addRenderableWidget(button);
        editor.updateChildren();

    }

    private Set<PerkButton> getAllButtons(Perk perk) {
        Set<PerkButton> buttons = Sets.newHashSet(perk.getButton());

        for (Perk child: perk.getChildren()) {
            buttons.add(child.getButton());
            getAllButtons(buttons, child);
        }

        updateScrollBoundaries(perk);
        return buttons;
    }

    private void getAllButtons(Set<PerkButton> buttons, Perk perk) {
        for (Perk child: perk.getChildren()) {
            buttons.add(child.getButton());
        }
    }

    public void refresh() {
        this.rebuildWidgets();
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // darkens the background screen
        this.renderBackground(graphics);
        this.renderPerk(root, graphics, mouseX, mouseY, partialTicks);
        //this is the format of how we draw text on the exampleScreen
        graphics.drawString(this.font,
                TITLE,
                this.leftPos + 8,
                this.topPos + 8,
                0x404040,
                false);
        drawSkillTreeTabs(graphics);
        // for dev purposes
        drawScrollValues(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    public void renderPerk(Perk perk, GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int sX = Mth.floor(scrollX);
        int sY = Mth.floor(scrollY);

        perk.updateButtonPosition(sX, sY);
        perk.renderButton(graphics, mouseX, mouseY, partialTicks);

    }

    private void drawScrollValues(GuiGraphics graphics) {
        graphics.drawString(this.font,
                Component.literal("root X: " + scrollX),
                8,
                8,
                Color.WHITE.hashCode(),
                true);
        graphics.drawString(this.font,
                Component.literal("root Y: " + scrollY),
                8,
                16,
                Color.WHITE.hashCode(),
                true);
        graphics.drawString(this.font,
                Component.literal("min X: " + minX + " max X: " + maxX),
                8,
                24,
                Color.WHITE.hashCode(),
                true);
        graphics.drawString(this.font,
                Component.literal("min Y: " + minY + " max Y: " + maxY),
                8,
                32,
                Color.WHITE.hashCode(),
                true);

    }

    private void drawSkillTreeTabs(GuiGraphics graphics) {
        int index = 0;
        for (SkillTreeTab tab: tabs.values()) {
            tab.drawTab(graphics, Mth.floor((double) width /2 - (tab.TEXTURE_WIDTH * (index - tabs.size()/2))), 0);
            index++;
        }
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (pButton != 1) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            }

            this.scroll(pDragX, pDragY);

            return true;
        }
    }

    public void scroll(double pDragX, double pDragY) {

        if (this.maxX - this.minX < this.width) {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, 16.0D, this.width - (this.maxX - this.minX));
        }
        else {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, -((this.maxX - this.minX) - width), 16.0D);
        }

        if (this.maxY - this.minY < this.height) {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, 16.0D, this.height - (this.maxY-this.minY));
        }
        else {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, -((this.maxY-this.minY) - height), 16.0D);

        }

    }

    public void updateScrollBoundaries(Perk perk) {
        PerkButton button = perk.getButton();

        int buttonLeft = button.getX();
        int buttonRight = button.getX() + button.getWidth();
        int buttonTop = button.getY();
        int buttonBottom = button.getY() + button.getHeight();

        this.minX = Math.min(this.minX, buttonLeft);
        this.maxX = Math.max(this.maxX, buttonRight);
        this.minY = Math.min(this.minY, buttonTop);
        this.maxY = Math.max(this.maxY, buttonBottom);

        for (Perk child: perk.getChildren()) {
            updateScrollBoundaries(child);
        }
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {

            for (SkillTreeTab tab : this.tabs.values()) {
                if (tab.getScreen() != this && tab.isMouseOver(pMouseX, pMouseY)) {
                    tabs.get(skill_type).selected(false);

                    if (tab.getScreen() == null) {
                        Minecraft.getInstance().setScreen(new SkillTreeScreen(tab.getSkill_type(), tabs));
                    } else {
                        Minecraft.getInstance().setScreen(tab.getScreen());
                    }
                    break;
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

//        atm it just closes the screen but could be used to switch tabs etc
        if (KeyBinding.OPEN_TESTSCREEN.matches(pKeyCode, pScanCode)) {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        } else if (KeyBinding.OPEN_EDITOR.matches(pKeyCode,pScanCode)) {
            this.minecraft.setScreen(editor);
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
