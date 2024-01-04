package net.dilger.sky_forge_mod.skill;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.IconType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkFrameType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Perk {

    protected final Component title = Component.literal("root");
    private static byte PERKIDCOUNT = 0;
    private byte perkID;
    private final ResourceLocation resourceLocation;
    private final SKILL_TYPE tree;
    private final PerkButton button;
    private final Perk parent;
    private ArrayList<Perk> children = new ArrayList<>();
    private final PerkDisplayInfo display;
    private final PerkReward reward;
    private final Requirement requirement;
    private boolean perkActive = false;




    public Perk(ResourceLocation resourceLocation, SKILL_TYPE tree, @Nullable ArrayList<Perk> children, @Nullable Perk parent, PerkDisplayInfo display, Requirement requirement, PerkReward reward) {
        this.resourceLocation = resourceLocation;
        this.tree = tree;
        this.children = children;
        this.parent = parent;
        this.display = display;
        this.button = new PerkButton(this,
                display.getFrame(),
                display.getRarity(),
                display.getIcon(),
                this::handlePerkButton);
        this.requirement = requirement;
        this.reward = reward;

        PerkList.add(this);
    }

    public static ResourceLocation resourceFromName(String name, SKILL_TYPE skill_type) {
        return new ResourceLocation(SkyForgeMod.MOD_ID,"perks/" + skill_type.getName() + "/" + name + ".json");
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

        if (!isActive()) {
            // make the packet change the player capability of which perks the player has unlocked
            requirement.payRequirements(getID());
            PerkList.updatePlayerPerks();

        }


    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
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

    public PerkReward getReward() {
        return reward;
    }

    public byte getID() {
        return this.perkID;
    }

    public boolean isActive() {
        return perkActive;
    }

    public void setActive(boolean perkActive) {
        this.perkActive = perkActive;
    }

    public void setID(byte ID) {
        this.perkID = ID;
    }

    // Builder
    public static class Builder {

        private final SKILL_TYPE tree;
        @Nullable
        private ArrayList<Perk> children;
        @Nullable
        private Perk parent;
        @Nullable
        private ResourceLocation parentId;
        private PerkDisplayInfo display;
        private PerkReward reward;
        private Requirement requirement;
        
        
        Builder(SKILL_TYPE tree, @Nullable ArrayList<Perk> children, @Nullable ResourceLocation parentId, PerkDisplayInfo display, Requirement requirement, PerkReward reward) {
            this.tree = tree;
            this.children = children;
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

                return new Perk(resourceLocation, this.tree, this.children, this.parent, this.display, this.requirement, this.reward);
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
        public static Perk.Builder fromJson(JsonObject pJson) {
            SKILL_TYPE tree = SKILL_TYPE.byName(GsonHelper.getAsString(pJson, "tree"));
            ArrayList<Perk> children = new ArrayList<>();
                    if (pJson.has("children")) {
                        JsonArray jsonArray = GsonHelper.getAsJsonArray(pJson, "children", new JsonArray());

                        for (JsonElement jsonElement: jsonArray) {
                            children.add(Perk.Builder.fromResourceLocation(resourceFromName(jsonElement.getAsString(), tree)));
                        }
                    }
            ResourceLocation resourcelocation = pJson.has("parent") ? new ResourceLocation(GsonHelper.getAsString(pJson, "parent")) : null;
            PerkDisplayInfo display = pJson.has("display") ? PerkDisplayInfo.fromJson(GsonHelper.getAsJsonObject(pJson, "display")) : null;
            PerkReward reward = PerkReward.deserialize(GsonHelper.getAsJsonObject(pJson, "reward"));
            Requirement requirements = Requirement.fromJson(GsonHelper.getAsJsonObject(pJson, "requirement"));

            return new Perk.Builder(tree, children, resourcelocation, display, requirements, reward);

        }
        public Requirement getRequirement() {
            return this.requirement;
        }

        public static Perk fromResourceLocation(ResourceLocation resource) {
            JsonParser parser = new JsonParser();
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Builder.class.getClassLoader()
                    .getResourceAsStream("data/" + resource.getNamespace() + "/" + resource.getPath()))));
            return Builder.fromJson((JsonObject) parser.parse(fileReader)).build(resource);
        }
    }

}
