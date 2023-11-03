package net.dilger.sky_forge_mod.gui.screen.skill;

import com.google.common.collect.Maps;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.SkillTreeButton;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
    private final Map<SKILL_TYPE, SkillTreeTab> skillTreeTabs = Maps.newHashMap();
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

        //create button
        addRenderableWidget(
                Button.builder(
                        EXAMPLE_BUTTON,
                        this::handleExampleButton)
                        .bounds(this.leftPos + 8, this.topPos + 20, 80, 20)
                        .tooltip(Tooltip.create(EXAMPLE_BUTTON))
                        .build());

        int index = 0;
        for (SKILL_TYPE skill_type: SKILL_TYPE.values()) {
            skillTreeTabs.put(skill_type ,new SkillTreeTab(null, skill_type, false));

            addRenderableWidget(
                    new SkillTreeButton(this.leftPos + 12 + 24 * index, this.topPos + 40, skill_type, skillTreeTabs)
            );
            index++;
        }
    }


    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // darkens the background screen
        this.renderBackground(graphics);
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        super.render(graphics, mouseX, mouseY, partialTicks);

        //this is the format of how we draw text on the exampleScreen
        graphics.drawString(this.font,
                TITLE,
                this.leftPos + 8,
                this.topPos + 8,
                0x404040,
                false);
    }

    private void handleExampleButton(Button button) {
        // logic here
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed a Button!"));
        if (Minecraft.getInstance().player.experienceLevel >= 15){
            Minecraft.getInstance().player.experienceLevel -= 15; //kind of works but doesn't tell the server that the player level has changed at all
            // we could use packets for this maybe?
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Stole some XP"));
        }
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
