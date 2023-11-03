package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.SkillTreeScreen;
import net.dilger.sky_forge_mod.gui.screen.skill.SkillTreeTab;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class SkillTreeButton extends ImageButton {

    SKILL_TYPE skill_type;

    public SkillTreeButton(int pX, int pY, SKILL_TYPE skill_type, Map<SKILL_TYPE, SkillTreeTab> tabs) {
        super(pX, pY, 20, 18, 0, 0, 18, getButtonTexture(skill_type), 20, 18,
                new OnPress() {
                    @Override
                    public void onPress(Button pButton) {
                        // open the skill tree screen
                        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed Skill Tree Button!").withStyle(ChatFormatting.DARK_PURPLE));

                        Minecraft.getInstance().setScreen(new SkillTreeScreen(skill_type, tabs));
                    }
                });

        this.skill_type = skill_type;
    }

    private static ResourceLocation getButtonTexture(SKILL_TYPE skill_type) {
        return new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/buttons/skill_tree/"+skill_type.toString().toLowerCase()+"_tree_button.png");

    }
}
