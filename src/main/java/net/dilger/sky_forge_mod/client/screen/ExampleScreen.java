package net.dilger.sky_forge_mod.client.screen;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.networking.ChangePlayerAtributes;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import com.mojang.authlib.GameProfile;


public class ExampleScreen extends Screen {
    private static final Component TITLE =
            Component.translatable("gui." + SkyForgeMod.MOD_ID + ".example_screen");
    private static final Component EXAMPLE_BUTTON =
            Component.translatable("gui." + SkyForgeMod.MOD_ID + ".example_screen.button.example_button");

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/example_block.png");

    private final int imageWidth, imageHeight;

    private int leftPos, topPos;

    private Button button;

    private static ChangePlayerAtributes ourPlayer = new ChangePlayerAtributes(Minecraft.getInstance().level.getServer(), Minecraft.getInstance().player.getGameProfile());

    public ExampleScreen() {
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
        this.button = addRenderableWidget(
                Button.builder(
                        EXAMPLE_BUTTON,
                        this::handleExampleButton)
                        .bounds(this.leftPos + 8, this.topPos + 20, 80, 20)
                        .tooltip(Tooltip.create(EXAMPLE_BUTTON))
                        .build());
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
            ourPlayer.subtractLevel(15);
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
