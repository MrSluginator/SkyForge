package net.dilger.sky_forge_mod.skill;

import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.gui.screen.skill.IconType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkFrameType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.conditions.ICondition;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

public class Perk {

    protected final Component title = Component.literal("root");
    private final ResourceLocation resourceLocation;
    private final SKILL_TYPE tree;
    private final PerkButton button;
    private final Perk parent;
    private final ArrayList<Perk> children = new ArrayList<>();
    private final PerkDisplayInfo display;
    private final PerkReward reward;
    private final Requirement requirement;




    public Perk(ResourceLocation resourceLocation, SKILL_TYPE tree, @Nullable Perk parent, PerkDisplayInfo display, Requirement requirement, PerkReward reward) {
        this.resourceLocation = resourceLocation;
        this.tree = tree;
        this.parent = parent;
        this.display = display;
        this.button = new PerkButton(this,
                display.getFrame(),
                display.getRarity(),
                display.getIcon(),
                this::handlePerkButton);
        this.requirement = requirement;
        this.reward = reward;
    }

//    display methods

    public void renderButton(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        button.drawConnectivity(graphics, false);

        if (!children.isEmpty()) {
            for (Perk child : children) {
                child.renderButton(graphics, pMouseX, pMouseY, pPartialTick);
            }
        }

        button.renderWidget(graphics, pMouseX, pMouseY, pPartialTick);
    }

    public void updateButtonPosition(int pX, int pY) {
        button.updatePosition(pX + display.getTreeX(), pY + display.getTreeY());

        if (!children.isEmpty()) {
            for (Perk child : children) {
                child.updateButtonPosition(pX, pY);
            }
        }
    }

    public PerkDisplayInfo getDisplay() {
        return this.display;
    }

    //    component methods

    private void handlePerkButton(Button button) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed " + display.getName().toString() + " perk!").withStyle(ChatFormatting.DARK_PURPLE));
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public SKILL_TYPE getTree() {
        return tree;
    }

    public PerkButton getButton() {
        return button;
    }

    @Nullable
    public Perk getParent() {
        return parent;
    }

    public void addChild(Perk child) {
        children.add(child);
    }
    public ArrayList<Perk> getChildren() {
        return children;
    }
    
    // Builder
    public static class Builder {

        private SKILL_TYPE tree;
        @Nullable
        private Perk parent;
        @Nullable
        private ResourceLocation parentId;
        private PerkDisplayInfo display;
        private PerkReward reward;
        private Requirement requirement;
        
        
        Builder(SKILL_TYPE tree, @Nullable ResourceLocation parentId, PerkDisplayInfo display, Requirement requirement, PerkReward reward) {
            this.tree = tree;
            this.parentId = parentId;
            this.display = display;
            this.reward = reward;
            this.requirement = requirement;
        }
        
        public Perk.Builder parent(Perk parent) { 
            this.parent = parent;
            return this;
        }
        public Perk.Builder parent(ResourceLocation parentId) {
            this.parentId = parentId;
            return this;
        }

        public Perk.Builder display(Component name, Component description, PerkFrameType frame, RarityType rarity, IconType icon) {
            return this.display(new PerkDisplayInfo(name, description, frame, rarity, icon));
        }

        public Perk.Builder display(PerkDisplayInfo display ) {
            this.display = display;
            return this;
        }

        public Perk.Builder reward(PerkReward.Builder rewardBuilder) {
            return this.reward(rewardBuilder.build());
        }

        public Perk.Builder reward(PerkReward reward) {
            this.reward = reward;
            return this;
        }

        public Perk.Builder addRequirement(int skillLevel, int xpCost, Item item, int itemCost) {
            this.requirement = new Requirement(this.tree, skillLevel, xpCost, item, itemCost);
            return this;
        }

        /**
         * Tries to resolve the parent of this advancement, if possible. Returns {@code true} on success.
         */
        public boolean canBuild(Function<ResourceLocation, Perk> pParentLookup) {
            if (this.parentId == null) {
                return true;
            } else {
                if (this.parent == null) {
                    this.parent = pParentLookup.apply(this.parentId);
                }

                return this.parent != null;
            }
        }

        public Perk build(ResourceLocation resourceLocation) {
            if (!this.canBuild((p_138407_) -> null)) {
                throw new IllegalStateException("Tried to build incomplete advancement!");
            } else {

                return new Perk(resourceLocation, this.tree, this.parent, this.display, this.requirement, this.reward);
            }
        }

        /**
         * Used for the generation of perks
         * -VanillaAdvancement class-
         */
        public Perk save(Consumer<Perk> pConsumer, String resourcelocation) {
            Perk perk = this.build(new ResourceLocation(resourcelocation));
            pConsumer.accept(perk);
            return perk;
        }

        /**
         * -AdvancementProvider class-
         *
         */
        public JsonObject serializeToJson() {

            JsonObject jsonobject = new JsonObject();
            if (this.parent != null) {
                jsonobject.addProperty("parent", this.parent.getResourceLocation().toString());
            } else if (this.parentId != null) {
                jsonobject.addProperty("parent", this.parentId.toString());
            }


            jsonobject.add("display", this.display.serializeToJson());
            

            jsonobject.add("reward", this.reward.serializeToJson());

            jsonobject.add("requirement", this.requirement.serializeToJson());
            
            return jsonobject;
        }

        public String toString() {
            return "Perk{parentId=" + this.parentId + ", display=" + this.display + ", reward=" + this.reward + ", requirement=" + this.requirement + "}";
        }

        /**
         * -ServerAdvancementManager class-
         *
         */
        public static Perk.Builder fromJson(JsonObject pJson, DeserializationContext pContext, ICondition.IContext context) {
            SKILL_TYPE tree = SKILL_TYPE.valueOf(GsonHelper.getAsString(pJson, "tree").toUpperCase());
            ResourceLocation resourcelocation = pJson.has("parent") ? new ResourceLocation(GsonHelper.getAsString(pJson, "parent")) : null;
            PerkDisplayInfo display = pJson.has("display") ? PerkDisplayInfo.fromJson(GsonHelper.getAsJsonObject(pJson, "display")) : null;
            PerkReward reward = PerkReward.deserialize(GsonHelper.getAsJsonObject(pJson, "reward"));
            Requirement requirements = Requirement.fromJson(GsonHelper.getAsJsonObject(pJson, "requirement"), pContext);

            return new Perk.Builder(tree, resourcelocation, display, requirements, reward);

        }
        public Requirement getRequirement() {
            return this.requirement;
        }
    }

}
