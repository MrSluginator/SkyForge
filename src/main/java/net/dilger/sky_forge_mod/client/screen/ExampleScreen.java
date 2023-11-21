package net.dilger.sky_forge_mod.client.screen;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.AffectPlayerLevel;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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

        LocalPlayer player = minecraft.getInstance().player;

        player.sendSystemMessage(Component.literal("Pressed a Button!"));
        if (player.experienceLevel >= 15){

            //to pass arguments must set the variable beforehand
            //its a static variable that exists inside the AffectPlayerLevel handle method

            //changes client side xp then changes tha amount to be subtracted in the AffectPlayer class. Then finally uses the messages.toServer to tell the server to update the xp amount aswell.
            player.experienceLevel -= 15;

            //this first line below wouldn't be needed if we could figure out how to send parameters into the modMessages
            AffectPlayerLevel.amount = -15;
            PacketHandling.sentToServer(new AffectPlayerLevel());

            // we could use packets for this maybe?
            player.sendSystemMessage(Component.literal("Stole some XP"));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100000, 6));


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