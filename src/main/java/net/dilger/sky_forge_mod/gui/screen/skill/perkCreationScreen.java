package net.dilger.sky_forge_mod.gui.screen.skill;

import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.dilger.sky_forge_mod.skill.Perk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class perkCreationScreen extends Screen {
    private final Perk parent;
    private final SkillTreeEditor editor;
    private CycleButton<PerkType> perkTypeCycleButton;
    private CycleButton<RarityType> rarityTypeCycleButton;
    public perkCreationScreen(SkillTreeEditor skillTreeEditor, Perk parent) {
        super(Component.literal(parent.toString()));
        this.editor = skillTreeEditor;
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();

        addRenderableWidget(Button.builder(Component.literal("add perk"), this::handleAddPerk)
                .bounds(4, 2, 60, 20)
                .build());
        perkTypeCycleButton = CycleButton.builder(PerkType::getName).withValues(PerkType.values()).create(4,24,120,20, Component.literal("perk type"));
        rarityTypeCycleButton = CycleButton.builder(RarityType::getName).withValues(RarityType.values()).create(4,46,120,20, Component.literal("perk type"));
        addRenderableWidget(perkTypeCycleButton);
        addRenderableWidget(rarityTypeCycleButton);
    }

    private void handleAddPerk(Button button) {
        if (getPerkType() == null || getRarity() == null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("TYPE and RARITY must not be null"));
            return;
        }
        Perk child = new Perk(parent, getPerkType(), getRarity(), getIcon());
        parent.addChild(child);
        editor.addButtonToScreen(child.getButton());
        Minecraft.getInstance().setScreen(editor);
    }

    private PerkType getPerkType() {
        return perkTypeCycleButton.getValue();
    }

    private RarityType getRarity() {
        return rarityTypeCycleButton.getValue();
    }

    private IconType getIcon() {
        return null;
    }
}
