package net.dilger.sky_forge_mod.gui.screen.skill;

import com.google.common.collect.Sets;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.dilger.sky_forge_mod.skill.Perk;
import net.dilger.sky_forge_mod.skill.PerkDisplayInfo;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
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
    private final Perk root;
//    private final PerkButton rootButton;
    private final int imageWidth, imageHeight;
    private final Map<SKILL_TYPE, SkillTreeTab> tabs;
    private int leftPos, topPos;

    private SKILL_TYPE skill_type;
    private boolean isScrolling;
    private double scrollX;
    private double scrollY;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;

    public SkillTreeScreen(SKILL_TYPE skill_type, Map<SKILL_TYPE, SkillTreeTab> tabs) {
        super(TITLE);

        this.imageWidth = 176;
        this.imageHeight = 166;

        this.tabs = tabs;

        // testing
        // need to find a better way to add buttons to the skill screen
        this.skill_type = skill_type;
        PerkDisplayInfo rootDisplay = new PerkDisplayInfo(Component.literal("root"), null);
        this.root = new Perk(null, PerkType.SQUARE, RarityType.COMMON, null);
        Perk child = new Perk(this.root, PerkType.SHIELD, RarityType.EPIC, null);
        this.root.addChild(child);
        Perk grandChild = new Perk(child, PerkType.CREST, RarityType.UNCOMMON, null);
        child.addChild(grandChild);

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

        //create buttons
        for (PerkButton perkButton: getAllButtons(root)) {
            // adding a widget puts it into the build in child set
            addButton(perkButton);
        }

    }

    private void addButton(Button button) {
        int buttonLeft = button.getX();
        int buttonRight = button.getX() + button.getWidth();
        int buttonTop = button.getY();
        int buttonBottom = button.getY() + button.getHeight();

        this.minX = Math.min(this.minX, buttonLeft);
        this.maxX = Math.max(this.maxX, buttonRight);
        this.minY = Math.min(this.minY, buttonTop);
        this.maxY = Math.max(this.maxY, buttonBottom);

        addRenderableWidget(button);
    }

    private Set<PerkButton> getAllButtons(Perk perk) {
        Set<PerkButton> buttons = Sets.newHashSet(perk.getButton());

        for (Perk child: perk.getChildren()) {
            buttons.add(child.getButton());
            getAllButtons(buttons, child);
        }

        return buttons;
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
    }

    public void renderPerk(Perk perk, GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        int sX = Mth.floor(scrollX);
        int sY = Mth.floor(scrollY);

        for (Perk child: perk.getChildren()) {
            renderPerk(child, graphics, mouseX, mouseY, partialTicks);
        }

        perk.updateButtonPosition(sX, sY);
        perk.getButton().render(graphics, mouseX, mouseY, partialTicks);

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
        // need to decide how much freedom the player has for scrolling
        // could make it just go until the extreme values are withing the screen

        this.scrollX = Mth.clamp(this.scrollX + pDragX, -(maxX - 26), this.width - (minX + 26));

        this.scrollY = Mth.clamp(this.scrollY + pDragY, -(maxY - 26), this.height - (minY + 26));

        /*
        bounds the scrolling to keep all elements inside the screen
        if (this.maxX - this.minX > 0) {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, 0.0D, Mth.abs(this.width - this.maxX));
        }

        if (this.maxY - this.minY > 0) {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, 0.0D, Mth.abs(this.height - this.maxY));
        }
        */

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
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
