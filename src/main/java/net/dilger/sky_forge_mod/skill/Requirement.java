package net.dilger.sky_forge_mod.skill;


import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import java.util.Map;


public class Requirement {
    private final PerkCondition condition;

    public Requirement(PerkCondition condition) {
        this.condition = condition;
    }

    public Requirement() {
        this.condition = null;
    }

    public static Requirement requirementFromJson(JsonObject pJson, DeserializationContext context) {
        ResourceLocation resourceLocation = new ResourceLocation(GsonHelper.getAsString(pJson, "condition"));
        PerkCondition condition = PerkConditions.getCriterion(resourceLocation);
        if (condition == null) {
            throw new JsonSyntaxException("Invalid condition: " + resourceLocation);
        }

        PerkCondition perkcondition = condition.createInstance(GsonHelper.getAsJsonObject(pJson, "requirement"), context);
        return new Requirement(perkcondition);
    }

    public static Map<String, Requirement> requirementsFromJson(JsonObject pJson, DeserializationContext context) {
        Map<String, Requirement> map = Maps.newHashMap();

        for (Map.Entry<String, JsonElement> entry : pJson.entrySet()) {
            map.put(entry.getKey(), requirementFromJson(GsonHelper.convertToJsonObject(entry.getValue(), "requirement"), context));
        }

        return map;
    }


    public PerkCondition getCondition() {
        return this.condition;
    }

    public JsonElement serializeToJson() {
        if (this.condition == null) {
            throw new JsonSyntaxException("Missing condition");
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("condition", this.condition.getResourceLocation().toString());

        return jsonObject;
    }
}
