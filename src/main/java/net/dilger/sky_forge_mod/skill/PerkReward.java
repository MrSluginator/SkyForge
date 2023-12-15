package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class PerkReward {
    @Nullable
    private final ResourceLocation[] commonBuffs;
    @Nullable
    private final ResourceLocation uniqueBuff;

    public PerkReward(@Nullable ResourceLocation[] commonBuffs, @Nullable ResourceLocation uniqueBuff) {
        this.commonBuffs = commonBuffs;
        this.uniqueBuff = uniqueBuff;
    }

    public void grantRewards() {
         
    }

    public String toString() {
        assert commonBuffs != null;
        return "PerkBuff{common_buffs=" + Arrays.toString(commonBuffs) + ", unique_buff=" + uniqueBuff + "}";
    }

    public JsonElement serializeToJson() {

        JsonObject jsonobject = new JsonObject();

        if (this.commonBuffs != null) {
            JsonArray jsonarray = new JsonArray();

            for(ResourceLocation resourcelocation : this.commonBuffs) {
                jsonarray.add(resourcelocation.toString());
            }

            jsonobject.add("common_buffs", jsonarray);
        }
        if (this.uniqueBuff != null) {
            jsonobject.addProperty("unique_buff", this.uniqueBuff.toString());
        }

        return jsonobject;

    }

    public static PerkReward deserialize(JsonObject pJson) throws JsonParseException {
        JsonArray jsonarray = GsonHelper.getAsJsonArray(pJson, "common_buffs", new JsonArray());
        ResourceLocation[] cpresourcelocation = new ResourceLocation[jsonarray.size()];

        for(int i = 0; i < cpresourcelocation.length; ++i) {
            cpresourcelocation[i] = new ResourceLocation(SkyForgeMod.MOD_ID, GsonHelper.convertToString(jsonarray.get(i), "common_buffs[" + i + "]"));
        }
        ResourceLocation upresourcelocation = pJson.has("unique_buff") ? new ResourceLocation(SkyForgeMod.MOD_ID, GsonHelper.getAsString(pJson, "unique_buff")) : null;

        return new PerkReward(cpresourcelocation, upresourcelocation );
    }

    public static class Builder {
        private final List<ResourceLocation> commonBuffs = Lists.newArrayList();
        private ResourceLocation uniqueBuff;

        public static PerkReward.Builder commonBuff(ResourceLocation perkBuffResource) {
            return (new PerkReward.Builder()).addCommonPerkBuff(perkBuffResource);
        }

        public PerkReward.Builder addCommonPerkBuff(ResourceLocation perkBuffResource) {
            this.commonBuffs.add(perkBuffResource);
            return this;
        }

        public static PerkReward.Builder uniqueBuff(ResourceLocation resourceLocation) {
            return (new PerkReward.Builder()).addUniquePerkBuff(resourceLocation);
        }

        public PerkReward.Builder addUniquePerkBuff(ResourceLocation perkBuffResource) {
            this.uniqueBuff = perkBuffResource;
            return this;
        }

        public PerkReward build() {
            return new PerkReward(this.commonBuffs.toArray(new ResourceLocation[0]), this.uniqueBuff);
        }
    }
}
