package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.SkillTreeScreen;
import net.dilger.sky_forge_mod.skills.PlayerSkillXp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;

public class SkillTreeButton extends ImageButton {

    PlayerSkillXp.SKILL_TYPE skill_type;

    public SkillTreeButton(int pX, int pY, PlayerSkillXp.SKILL_TYPE skill_type) {
        super(pX, pY, 20, 18, 0, 0, 18, getButtonTexture(skill_type), 20, 18,
                new OnPress() {
                    @Override
                    public void onPress(Button pButton) {
                        // open the skill tree screen
                        Minecraft.getInstance().setScreen(new SkillTreeScreen(skill_type));
                    }
                });

        this.skill_type = skill_type;
    }
    public SkillTreeButton(int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, ResourceLocation pResourceLocation, OnPress pOnPress) {
        super(pX, pY, 20, 18, 0, 0, pHeight, pResourceLocation, 20, 18, pOnPress);
    }

    /*private void handleSkillTreeButton(Button button) {
        // open the skill tree screen
        Minecraft.getInstance().setScreen(new SkillTreeScreen(skill_type));
    }*/

    private static ResourceLocation getButtonTexture(PlayerSkillXp.SKILL_TYPE skill_type) {
        return new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/buttons/"+skill_type.toString().toLowerCase()+"_tree_button.png");

    }
}
