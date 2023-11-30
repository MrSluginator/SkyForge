package net.dilger.sky_forge_mod.gui.screen.skill.editor;

import com.google.gson.JsonSyntaxException;
import net.dilger.sky_forge_mod.skill.Requirement;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class RequirmentsCreationScreen extends Screen {
    private final PerkCreationScreen main;
    private final SKILL_TYPE skillType;
    private EditBox skillLevelEditBox;
    private EditBox xpCostEditBox;
    private EditBox itemEditBox;
    private EditBox itemCostEditBox;
    private Requirement requirement;

    protected RequirmentsCreationScreen(PerkCreationScreen main, SKILL_TYPE skillType) {
        super(Component.literal("Requirements"));
        this.main = main;
        this.skillType = skillType;
    }

    @Override
    protected void init() {
        super.init();

        skillLevelEditBox = new EditBox(this.font, 4, 24, 240, 20, Component.literal("enter skill level"));
        xpCostEditBox = new EditBox(this.font, 4, 48, 240, 20, Component.literal("enter xp cost"));
        itemEditBox = new EditBox(this.font, 4, 72, 240, 20, Component.literal("enter item name"));
        itemCostEditBox = new EditBox(this.font, 4, 94, 240, 20, Component.literal("enter item cost"));

        addRenderableWidget(skillLevelEditBox);
        addRenderableWidget(xpCostEditBox);
        addRenderableWidget(itemEditBox);
        addRenderableWidget(itemCostEditBox);

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
        saveRequirements();
        handleExit(button);
    }

    private int getSkillLevel() {
        try {
            return Integer.parseInt(skillLevelEditBox.getValue());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
    private int getXpCost() {
        try {
            return Integer.parseInt(xpCostEditBox.getValue());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
    private Item getItem() {
        try {
            return ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemEditBox.getValue()));
        } catch (JsonSyntaxException jse) {
            return null;
        }
    }
    private int getItemCost() {
        try {
            return Integer.parseInt(itemCostEditBox.getValue());
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    public void saveRequirements() {
        main.setRequirement(new Requirement(
                this.skillType,
                getSkillLevel(),
                getXpCost(),
                getItem(),
                getItemCost()
        ));
    }
}
