package net.dilger.sky_forge_mod.gui.screen.skill;

import com.google.common.collect.Maps;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
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

import java.util.Map;

public class SkillScreen extends Screen {
    private static final Component TITLE =
            Component.translatable("gui.sky_forge_mod.skill_screen");
    private static final Component EXAMPLE_BUTTON =
            Component.translatable("gui.sky_forge_mod.skill_screen.button.example_button");

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/example_block.png");
    private final Map<SKILL_TYPE, SkillTreeTab> tabs = Maps.newHashMap();
    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private Button button;

    public SkillScreen() {
        super(TITLE);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        if(this.minecraft == null) return;
        Level level = this.minecraft.level;
        if(level == null) return;

        for (SKILL_TYPE skill_type: SKILL_TYPE.values()) {
            tabs.put(skill_type ,new SkillTreeTab(null, skill_type, false));
        }
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // darkens the background screen
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTicks);

        drawSkillTreeTabs(graphics);

        //this is the format of how we draw text on the exampleScreen
        graphics.drawString(this.font,
                TITLE,
                this.leftPos + 8,
                this.topPos + 8,
                0x404040,
                false);
    }

    private void drawSkillTreeTabs(GuiGraphics graphics) {
        int index = 0;
        for (SkillTreeTab tab: tabs.values()) {
            tab.drawTab(graphics, Mth.floor((double) width / 2 - (SkillTreeTab.TEXTURE_WIDTH * (index - (double) tabs.size() /2))), 0, true);
            index++;
        }
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

//        atm it just closes the screen but could be used to switch tabs etc
        if (KeyBinding.OPEN_SKILL_SCREEN.matches(pKeyCode, pScanCode)) {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {

            for (SkillTreeTab tab : this.tabs.values()) {
                if (tab.isMouseOver(pMouseX, pMouseY)) {

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

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
