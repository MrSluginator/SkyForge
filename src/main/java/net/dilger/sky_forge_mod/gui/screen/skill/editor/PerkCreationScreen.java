package net.dilger.sky_forge_mod.gui.screen.skill.editor;

import net.dilger.sky_forge_mod.skill.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

public class PerkCreationScreen extends Screen {
    private final Perk parent;
    private final SkillTreeEditor editor;
    private final SKILL_TYPE skill_type;
    private PerkDisplayInfo display;
    private String name;
    private PerkReward reward;
    private Requirement requirement;

    public PerkCreationScreen(SkillTreeEditor skillTreeEditor, SKILL_TYPE skill_type, @Nullable Perk parent) {
        super(Component.literal(parent != null ? parent.toString() : "root"));
        this.editor = skillTreeEditor;
        this.skill_type = skill_type;
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(Button.builder(Component.literal("set display info"), this::handleSetDisplayInfo)
                .bounds(4, 4, 120, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("set requirements"), this::handleSetRequirements)
                .bounds(4, 4, 120, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("set reward"), this::handleSetReward)
                .bounds(4, 4, 120, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("add perk"), this::handleAddPerk)
                .bounds(240, 28, 60, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("exit"), this::handleExit)
                .bounds(240, 4, 60, 20)
                .build());
    }

    private void handleExit(Button button) {
        Minecraft.getInstance().setScreen(editor);
    }

    private void handleAddPerk(Button button) {
        ResourceLocation resourceLocation = new ResourceLocation(this.skill_type.toString().toLowerCase()+ "/" + name);
        Perk child = new Perk(
                resourceLocation,
                skill_type,
                parent,
                display,
                requirement,
                reward);

        parent.addChild(child);
        handleExit(button);
    }

    private void handleSetDisplayInfo(Button button) {
        Minecraft.getInstance().setScreen(new DisplayInfoCreationScreen(this));
    }

    private void handleSetRequirements(Button button) {
        Minecraft.getInstance().setScreen(new RequirmentsCreationScreen(this));
    }

    private void handleSetReward(Button button) {
        Minecraft.getInstance().setScreen(new RewardCreationScreen(this));
    }

    public void setReward(PerkReward reward) {
        this.reward = reward;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }


    public void setDisplayInfo(PerkDisplayInfo perkDisplayInfo) {
        this.display = perkDisplayInfo;
    }

    public void setPerkName(String perkName) {
        this.name = perkName;
    }
}
