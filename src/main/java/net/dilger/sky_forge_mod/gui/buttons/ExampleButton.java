package net.dilger.sky_forge_mod.gui.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ExampleButton extends ImageButton {
    protected static final ResourceLocation BUTTON_RESOURCE_LOCATION = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skills/skill_button.png");
    protected static final Button.CreateNarration DEFAULT_NARRATION = (p_253298_) -> {
        return p_253298_.get();
    };
    public ExampleButton() {
        super(0, 0, 20, 18, 0, 0, 19, BUTTON_RESOURCE_LOCATION, (p_289630_) -> {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed Button!"));
        });
    }

    @Override
    public void onPress() {

        System.out.print("Button Pressed !!!");
        super.onPress();
    }
}
