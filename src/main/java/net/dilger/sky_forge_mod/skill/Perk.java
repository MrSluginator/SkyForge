package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.Set;

public class Perk {

    private final PerkButton button;
    protected final Component title = Component.literal("root");
    private final Perk parent;
    private final Set<Perk> children = Sets.newLinkedHashSet();

    public Perk(@Nullable Perk parent) {
        this.parent = parent;
        // textures are 26x26
        this.button = new PerkButton(this, 30 * getAncestryLevel(this), 0, 0, 0, this::handlePerkButton);
    }

//    display methods
    private int getAncestryLevel(Perk child) {
        int level = 0;

        while(true) {
            Perk parent = child.getParent();
            if (parent == null) {
                return level;
            }

            level++;
            child = parent;
        }
    }

//    component methods
    private void handlePerkButton(Button button) {
        Minecraft.getInstance().player.sendSystemMessage(Component.literal("Pressed " + this.title + " Button!").withStyle(ChatFormatting.DARK_PURPLE));
    }

    public PerkButton getButton() {
        return button;
    }

    public Perk getParent() {
        return parent;
    }

    public void addChild(Perk child) {
        children.add(child);
    }
    public Set<Perk> getChildren() {
        return children;
    }

    /*@Nullable
    private final Perk parent;
    @Nullable
    private final PerkDisplayInfo displayInfo;
    private final ResourceLocation perkData;
    private final Set<Perk> children = Sets.newLinkedHashSet();

    public Perk(ResourceLocation perkData, @Nullable Perk parent, @Nullable PerkDisplayInfo displayInfo) {
        this.perkData = perkData;
        this.displayInfo = displayInfo;
        this.parent = parent;

        if (parent != null) {
            parent.addChild(this);
        }

    }

    *//**
     * @return The parent advancement to display in the advancements screen or {@code null} to signify this is a root
     * advancement.
     *//*
    @Nullable
    public Perk getParent() {
        return this.parent;
    }

    public Perk getRoot() {
        return getRoot(this);
    }

    public static Perk getRoot(Perk perk) {
        Perk childPerk = perk;
        // check if the perk's parent is null meaning it is a root
        // else set the parent as the new perk and re check
        while(true) {
            Perk parentPerk = childPerk.getParent();
            if (parentPerk == null) {
                return childPerk;
            }

            childPerk = parentPerk;
        }
    }

    *//**
     * @return Display information for this perk, or {@code null} if this perk is invisible.
     *//*
    @Nullable
    public PerkDisplayInfo getDisplay() {
        return this.displayInfo;
    }

    public String toString() {
        return "SimplePerk{id=" + this.getPerkData() + ", parent=" + (this.parent == null ? "null" : this.parent.getPerkData()) + ", display=" + this.displayInfo + "}";
    }

    *//**
     * @return An iterable through this perk's children.
     *//*
    public Iterable<Perk> getChildren() {
        return this.children;
    }

    *//**
     * Add the provided {@code child} as a child of this perk.
     *//*
    public void addChild(Perk child) {
        this.children.add(child);
    }
    *//**
     * @return The data location of this Perk.
     *//*
    public ResourceLocation getPerkData() {
        return this.perkData;
    }

    *//*public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else if (!(pOther instanceof Perk perk)) {
            return false;
        } else {
            return this.perkData.equals(perk.perkData);
        }
    }*//*

    public static class Builder {
        @Nullable
        private ResourceLocation parentData;
        @Nullable
        private Perk parent;
        @Nullable
        private PerkDisplayInfo displayInfo;
//        private PerkRewards rewards = PerkRewards.EMPTY;
//        private Map<String, Criterion> criteria = Maps.newLinkedHashMap();
//        @Nullable
//        private String[][] requirements;
//        private RequirementsStrategy requirementsStrategy = RequirementsStrategy.AND;
//        private final boolean sendsTelemetryEvent;

        Builder(@Nullable ResourceLocation parentData, @Nullable PerkDisplayInfo displayInfo) {
            this.parentData = parentData;
            this.displayInfo = displayInfo;
//            this.rewards = pRewards;
//            this.criteria = pCriteria;
//            this.requirements = pRequirements;
//            this.sendsTelemetryEvent = pSendsTelemetryEvent;
        }

        *//*private Builder(boolean pSendsTelemetryEvent) {
            this.sendsTelemetryEvent = pSendsTelemetryEvent;
        }

        public static Perk.Builder advancement() {
            return new Perk.Builder(true);
        }

        public static Perk.Builder recipePerk() {
            return new Perk.Builder(false);
        }
*//*
        public Perk.Builder parent(Perk parent) {
            this.parent = parent;
            return this;
        }

        public Perk.Builder parent(ResourceLocation pParentId) {
            this.parentData = pParentId;
            return this;
        }

        public Perk.Builder display(ItemStack pStack, Component pTitle, Component pDescription, @Nullable ResourceLocation pBackground, FrameType pFrame, boolean pShowToast, boolean pAnnounceToChat, boolean pHidden) {
            return this.display(new PerkDisplayInfo(pTitle, pBackground));
        }

        public Perk.Builder display(ItemLike pItem, Component pTitle, Component pDescription, @Nullable ResourceLocation pBackground, FrameType pFrame, boolean pShowToast, boolean pAnnounceToChat, boolean pHidden) {
            return this.display(new PerkDisplayInfo(pTitle, pBackground));
        }

        public Perk.Builder display(PerkDisplayInfo pDisplay) {
            this.displayInfo = pDisplay;
            return this;
        }



        *//**
         * Tries to resolve the parent of this advancement, if possible. Returns {@code true} on success.
         *//*
        public boolean canBuild(Function<ResourceLocation, Perk> pParentLookup) {
            if (this.parentData == null) {
                return true;
            } else {
                if (this.parent == null) {
                    this.parent = pParentLookup.apply(this.parentData);
                }

                return this.parent != null;
            }
        }

        public Perk build(ResourceLocation pId) {
            if (!this.canBuild((p_138407_) -> {
                return null;
            })) {
                throw new IllegalStateException("Tried to build incomplete advancement!");
            } else {
//                if (this.requirements == null) {
//                    this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
//                }

                return new Perk(pId, this.parent, this.displayInfo);
            }
        }

        public Perk save(Consumer<Perk> pConsumer, String pId) {
            Perk advancement = this.build(new ResourceLocation(pId));
            pConsumer.accept(advancement);
            return advancement;
        }

        public JsonObject serializeToJson() {
            *//*if (this.requirements == null) {
                this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
            }*//*

            JsonObject jsonobject = new JsonObject();
            if (this.parent != null) {
                jsonobject.addProperty("parent", this.parent.getPerkData().toString());
            } else if (this.parentData != null) {
                jsonobject.addProperty("parent", this.parentData.toString());
            }

            if (this.displayInfo != null) {
                jsonobject.add("display", this.displayInfo.serializeToJson());
            }

//            jsonobject.add("rewards", this.rewards.serializeToJson());
            *//*JsonObject jsonobject1 = new JsonObject();

            for(Map.Entry<String, Criterion> entry : this.criteria.entrySet()) {
                jsonobject1.add(entry.getKey(), entry.getValue().serializeToJson());
            }*//*

            *//*jsonobject.add("criteria", jsonobject1);
            JsonArray jsonarray1 = new JsonArray();

            for(String[] astring : this.requirements) {
                JsonArray jsonarray = new JsonArray();

                for(String s : astring) {
                    jsonarray.add(s);
                }

                jsonarray1.add(jsonarray);
            }*//*

//            jsonobject.add("requirements", jsonarray1);
//            jsonobject.addProperty("sends_telemetry_event", this.sendsTelemetryEvent);
            return jsonobject;
        }

        public void serializeToNetwork(FriendlyByteBuf pBuffer) {
            *//*if (this.requirements == null) {
                this.requirements = this.requirementsStrategy.createRequirements(this.criteria.keySet());
            }*//*

            pBuffer.writeNullable(this.parentData, FriendlyByteBuf::writeResourceLocation);
            pBuffer.writeNullable(this.displayInfo, (p_214831_, p_214832_) -> {
                p_214832_.serializeToNetwork(p_214831_);
            });
            *//*Criterion.serializeToNetwork(this.criteria, pBuffer);
            pBuffer.writeVarInt(this.requirements.length);

            for(String[] astring : this.requirements) {
                pBuffer.writeVarInt(astring.length);

                for(String s : astring) {
                    pBuffer.writeUtf(s);
                }
            }

            pBuffer.writeBoolean(this.sendsTelemetryEvent);*//*
        }

        public String toString() {
            return "Task Perk{parentId=" + this.parentData + ", display=" + this.displayInfo + "}";
        }

        *//** @deprecated Forge: use {@linkplain #fromJson(JsonObject, DeserializationContext, net.minecraftforge.common.crafting.conditions.ICondition.IContext) overload with context}. *//*
        @Deprecated
        public static Perk.Builder fromJson(JsonObject pJson, DeserializationContext pContext) {
            return fromJson(pJson, pContext, net.minecraftforge.common.crafting.conditions.ICondition.IContext.EMPTY);
        }

        public static Perk.Builder fromJson(JsonObject pJson, DeserializationContext pContext, net.minecraftforge.common.crafting.conditions.ICondition.IContext context) {
//            if ((pJson = net.minecraftforge.common.crafting.ConditionalPerk.processConditional(pJson, context)) == null) return null;
            ResourceLocation resourcelocation = pJson.has("parent") ? new ResourceLocation(GsonHelper.getAsString(pJson, "parent")) : null;
            PerkDisplayInfo displayinfo = pJson.has("display") ? PerkDisplayInfo.fromJson(GsonHelper.getAsJsonObject(pJson, "display")) : null;
//            PerkRewards advancementrewards = pJson.has("rewards") ? PerkRewards.deserialize(GsonHelper.getAsJsonObject(pJson, "rewards")) : PerkRewards.EMPTY;
            *//*Map<String, Criterion> map = Criterion.criteriaFromJson(GsonHelper.getAsJsonObject(pJson, "criteria"), pContext);
            if (map.isEmpty()) {
                throw new JsonSyntaxException("Perk criteria cannot be empty");
            } else {
                JsonArray jsonarray = GsonHelper.getAsJsonArray(pJson, "requirements", new JsonArray());
                String[][] astring = new String[jsonarray.size()][];

                for(int i = 0; i < jsonarray.size(); ++i) {
                    JsonArray jsonarray1 = GsonHelper.convertToJsonArray(jsonarray.get(i), "requirements[" + i + "]");
                    astring[i] = new String[jsonarray1.size()];

                    for(int j = 0; j < jsonarray1.size(); ++j) {
                        astring[i][j] = GsonHelper.convertToString(jsonarray1.get(j), "requirements[" + i + "][" + j + "]");
                    }
                }

                if (astring.length == 0) {
                    astring = new String[map.size()][];
                    int k = 0;

                    for(String s2 : map.keySet()) {
                        astring[k++] = new String[]{s2};
                    }
                }

                for(String[] astring1 : astring) {
                    if (astring1.length == 0 && map.isEmpty()) {
                        throw new JsonSyntaxException("Requirement entry cannot be empty");
                    }

                    for(String s : astring1) {
                        if (!map.containsKey(s)) {
                            throw new JsonSyntaxException("Unknown required criterion '" + s + "'");
                        }
                    }
                }

                for(String s1 : map.keySet()) {
                    boolean flag1 = false;

                    for(String[] astring2 : astring) {
                        if (ArrayUtils.contains(astring2, s1)) {
                            flag1 = true;
                            break;
                        }
                    }

                    if (!flag1) {
                        throw new JsonSyntaxException("Criterion '" + s1 + "' isn't a requirement for completion. This isn't supported behaviour, all criteria must be required.");
                    }
                }

                boolean flag = GsonHelper.getAsBoolean(pJson, "sends_telemetry_event", false);
                return new Perk.Builder(resourcelocation, displayinfo, advancementrewards, map, astring, flag);
            }*//*
            return new Perk.Builder(resourcelocation, displayinfo);

        }

        public static Perk.Builder fromNetwork(FriendlyByteBuf pBuffer) {
            ResourceLocation resourcelocation = pBuffer.readNullable(FriendlyByteBuf::readResourceLocation);
            PerkDisplayInfo displayinfo = pBuffer.readNullable(PerkDisplayInfo::fromNetwork);
            Map<String, Criterion> map = Criterion.criteriaFromNetwork(pBuffer);
            String[][] astring = new String[pBuffer.readVarInt()][];

            for(int i = 0; i < astring.length; ++i) {
                astring[i] = new String[pBuffer.readVarInt()];

                for(int j = 0; j < astring[i].length; ++j) {
                    astring[i][j] = pBuffer.readUtf();
                }
            }

            boolean flag = pBuffer.readBoolean();
            return new Perk.Builder(resourcelocation, displayinfo);
        }

//        public Map<String, Criterion> getCriteria() {
//            return this.criteria;
//        }
    }*/
}
