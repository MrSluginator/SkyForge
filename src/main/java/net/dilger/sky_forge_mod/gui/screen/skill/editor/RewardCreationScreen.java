package net.dilger.sky_forge_mod.gui.screen.skill.editor;

import net.dilger.sky_forge_mod.gui.screen.skill.IconType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkFrameType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.dilger.sky_forge_mod.skill.PerkReward;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class RewardCreationScreen extends Screen {
    private final PerkCreationScreen main;
    private CycleButton<PerkFrameType> perkTypeCycleButton;
    private CycleButton<RarityType> rarityTypeCycleButton;
    private EditBox perkNameEditBox;
    private EditBox perkDescriptionEditBox;
    private ArrayList<ResourceLocation> commonBuffs;

    protected RewardCreationScreen(PerkCreationScreen main) {
        super(Component.literal("Reward"));
        this.main = main;
    }

    @Override
    protected void init() {
        super.init();

        perkTypeCycleButton = CycleButton.builder(PerkFrameType::getName).withValues(PerkFrameType.values()).create(4,24,120,20, Component.literal("perk type"));
        rarityTypeCycleButton = CycleButton.builder(RarityType::getName).withValues(RarityType.values()).create(4,46,120,20, Component.literal("perk type"));
        perkNameEditBox = new EditBox(this.font, 4, 68, 240, 20, Component.literal("enter perk name"));
        perkDescriptionEditBox = new EditBox(this.font, 4, 90, 240, 40, Component.literal("enter perk description"));

        addRenderableWidget(perkTypeCycleButton);
        addRenderableWidget(rarityTypeCycleButton);
        addRenderableWidget(perkNameEditBox);
        addRenderableWidget(perkDescriptionEditBox);

        addRenderableWidget(Button.builder(Component.literal("exit"), this::handleExit)
                .bounds(240, 4, 60, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal("save"), this::handleSave)
                .bounds(240, 28, 60, 20)
                .build());
    }

    private void handleExit(Button button) {
        Minecraft.getInstance().setScreen(main);
    }
    private void handleSave(Button button) {
        saveReward();
        handleExit(button);
    }

    private PerkFrameType getUniqueBuff() {
        return perkTypeCycleButton.getValue();
    }

    private void addCommonBuff(ResourceLocation resourceLocation) {
        commonBuffs.add(resourceLocation);
    }

    private IconType getIcon() {
        return null;
    }

    private String getPerkName() {
        return perkNameEditBox.getValue();
    }

    private String getPerkDescription() {
        return perkDescriptionEditBox.getValue();
    }

    public void saveReward() {
        main.setReward(new PerkReward(null, null));
    }
}
