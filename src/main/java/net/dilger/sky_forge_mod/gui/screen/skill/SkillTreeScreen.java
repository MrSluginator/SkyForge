package net.dilger.sky_forge_mod.gui.screen.skill;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.skills.PlayerSkillXp;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SkillTreeScreen extends Screen {


    private static final Component TITLE =
            Component.translatable("gui." + SkyForgeMod.MOD_ID + ".skill_tree_screen");

    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    private PlayerSkillXp.SKILL_TYPE skill_type;
    public SkillTreeScreen(PlayerSkillXp.SKILL_TYPE skill_type) {
        super(TITLE);

        this.imageWidth = 176;
        this.imageHeight = 166;

        this.skill_type = skill_type;
    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        if(this.minecraft == null) return;
        Level level = this.minecraft.level;
        if(level == null) return;

        //create buttons

    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // darkens the background screen
        this.renderBackground(graphics);
//        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(graphics, mouseX, mouseY, partialTicks);

        //this is the format of how we draw text on the exampleScreen
        graphics.drawString(this.font,
                TITLE,
                this.leftPos + 8,
                this.topPos + 8,
                0x404040,
                false);
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
