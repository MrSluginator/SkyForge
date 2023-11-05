package net.dilger.sky_forge_mod.gui.screen.skill;

import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.skill.Perk;
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

    protected SkillTreeEditor(SkillTreeScreen screen) {
        super(Component.literal("Editor"));

        this.screen = screen;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        screen.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.drawString(this.font,
                Component.literal("editor"),
                width - 60,
                4,
                Color.GREEN.hashCode(),
                false);
    }

    @Override
    protected void init() {
        super.init();

        updateChildren();
    }

    public void addButtonToScreen(Button button) {
        screen.addButton(button);
    }
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {
            // TODO: make this more efficient with get focused()
            for (GuiEventListener button: this.children()) {
                if (button instanceof PerkButton) {
                    if (button.isMouseOver(pMouseX, pMouseY)) {
                        System.out.println("clicked a button : " + button.toString());
                        openPerkCreationScreen(((PerkButton) button).getPerk());

                    }
                }
            }

        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

        if (KeyBinding.OPEN_EDITOR.matches(pKeyCode,pScanCode)) {
            screen.refresh();
            this.minecraft.setScreen(screen);
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    private void openPerkCreationScreen(Perk perk) {
        this.minecraft.setScreen(new perkCreationScreen(this, perk));
    }

    public void updateChildren() {
        this.children().addAll((Collection) screen.children());
    }
}
