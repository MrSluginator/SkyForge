package net.dilger.sky_forge_mod.gui.screen.skill.editor;

import net.dilger.sky_forge_mod.gui.screen.skill.SkillTreeScreen;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.skill.Perk;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.awt.*;
import java.util.Collection;

public class SkillTreeEditor extends Screen {

    private final SkillTreeScreen screen;
    private final SKILL_TYPE skill_type;
    private boolean buttonHovered = false;

    public SkillTreeEditor(SkillTreeScreen screen, SKILL_TYPE skill_type) {
        super(Component.literal("Editor"));

        this.screen = screen;
        this.skill_type = skill_type;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        screen.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        buttonHovered = false;
        for (GuiEventListener button: this.children()) {
            if (button instanceof PerkButton) {
                if (button.isMouseOver(pMouseX, pMouseY)) {
                    buttonHovered = true;

                }
            }
        }

        pGuiGraphics.drawString(this.font,
                Component.literal("editor"),
                width - 60,
                4,
                Color.GREEN.hashCode(),
                false);
        pGuiGraphics.drawString(this.font,
                Component.literal("button hovered: "+buttonHovered),
                4,
                4,
                Color.GREEN.hashCode(),
                false);
    }

    @Override
    protected void init() {
        super.init();

        System.out.println("EDITOR INIT");

        updateChildren();
    }

    public void addButtonToScreen(Button button) {
        //screen.addButton(button);
    }
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {
            // TODO: make this more efficient with get focused()
            if (this.children().isEmpty()) {
                openPerkCreationScreen();
            }
            else {
                for (GuiEventListener button : this.children()) {
                    if (button instanceof PerkButton) {
                        if (button.isMouseOver(pMouseX, pMouseY)) {
                            openPerkCreationScreen(((PerkButton) button).getPerk());

                        }
                    }
                }
            }

        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

        if (KeyBinding.OPEN_EDITOR.matches(pKeyCode,pScanCode)) {
            this.minecraft.setScreen(screen);
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    private void openPerkCreationScreen(Perk perk) {
        this.minecraft.setScreen(new PerkCreationScreen(this, this.skill_type, perk));
    }

    private void openPerkCreationScreen() {
        this.openPerkCreationScreen(null);
    }

    public void updateChildren() {
        System.out.println("UPDATE CHILDREN");
        this.children().addAll((Collection) screen.children());
        for (GuiEventListener button: children()) {
            System.out.println("editor: " + button.toString());
        }
    }

    public void setRoot(Perk root) {
        this.screen.setRoot(root);
    }
}
