package net.dilger.sky_forge_mod.gui.screen.skill;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkFrameType;
import net.dilger.sky_forge_mod.gui.screen.skill.editor.SkillTreeEditor;
import net.dilger.sky_forge_mod.skill.Perk;
import net.dilger.sky_forge_mod.skill.PerkDisplayInfo;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.dilger.sky_forge_mod.skill.SkillTreeNodePosition;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
    private Perk root;
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
    private int treeWidth = 0;
    private int treeHeight = 0;

    public SkillTreeScreen(SKILL_TYPE skill_type, Map<SKILL_TYPE, SkillTreeTab> tabs) {
        super(TITLE);

        this.tabs = tabs;
        this.skill_type = skill_type;

        editor = new SkillTreeEditor(this, skill_type);

    }


    @Override
    protected void init() {
        super.init();

        ResourceLocation rootResource = new ResourceLocation(SkyForgeMod.MOD_ID,"perks/" + skill_type.getName() + "/root.json");
        root = Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", skill_type));



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

        if (root != null) {
            // position buttons
            SkillTreeNodePosition.run(root);
            // add buttons to screen
            addPerkButtonToScreen(root);
        }

        this.treeWidth = this.maxX - this.minX;
        this.treeHeight = this.maxY - this.minY;

        editor.updateChildren();

    }

    @Override
    public void onClose() {
        super.onClose();
        System.out.println("on close");
        if (this.root != null) {

        }
    }

    public void setRoot(Perk root) {
        this.root = root;
    }

    public void addButton(Button button) {
        if (root != null) SkillTreeNodePosition.run(root);
        addRenderableWidget(button);
        editor.updateChildren();

    }

    private void addPerkButtonToScreen(Perk perk) {
        addRenderableWidget(perk.getButton());

        for (Perk child: perk.getChildren()) {
            addPerkButtonToScreen(child);
        }

        updateTreeSize(perk);

    }

    private void getAllButtons(Set<PerkButton> buttons, Perk perk) {
        for (Perk child: perk.getChildren()) {
            buttons.add(child.getButton());
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // darkens the background screen
        this.renderBackground(graphics);
        if (root != null) this.renderPerk(root, graphics, mouseX, mouseY, partialTicks);
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
    }

    public void renderPerk(Perk perk, GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int sX = Mth.floor(this.scrollX);
        int sY = Mth.floor(this.scrollY);

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
                Component.literal("Screen X: " + this.width + " Tree X: " + treeWidth),
                8,
                24,
                Color.WHITE.hashCode(),
                true);
        graphics.drawString(this.font,
                Component.literal("Screen Y: " + this.height + " Tree Y: " + treeHeight),
                8,
                32,
                Color.WHITE.hashCode(),
                true);

    }

    private void drawSkillTreeTabs(GuiGraphics graphics) {
        int index = 0;
        for (SkillTreeTab tab: tabs.values()) {
            tab.drawTab(graphics, Mth.floor((double) width / 2 - (SkillTreeTab.TEXTURE_WIDTH * (index - (double) tabs.size() /2))), 0, false);
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

        if (this.treeWidth < this.width) {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, 0.0D, this.width - this.treeWidth);
        }
        else {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, -(this.treeWidth - this.width), 0.0D);
        }

        if (this.treeHeight < this.height) {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, 0.0D, this.height - this.treeHeight);
        }
        else {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, -(this.treeHeight - this.height), 0.0D);

        }

    }

    public void updateTreeSize(Perk perk) {
        int blockSize = PerkFrameType.getBlockSize();
        PerkDisplayInfo perkDisplay = perk.getDisplay();

        int perkLeft = perkDisplay.getTreeX();
        int perkRight = perkDisplay.getTreeX() + blockSize;
        int perkTop = perkDisplay.getTreeY();
        int perkBottom = perkDisplay.getTreeY() + blockSize;

        this.minX = Math.min(this.minX, perkLeft);
        this.maxX = Math.max(this.maxX, perkRight);
        this.minY = Math.min(this.minY, perkTop);
        this.maxY = Math.max(this.maxY, perkBottom);

        for (Perk child: perk.getChildren()) {
            updateTreeSize(child);
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
