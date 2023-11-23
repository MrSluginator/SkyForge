package net.dilger.sky_forge_mod.skill;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;

public interface Buff<T> {
    ResourceLocation getPacketResourceLocation();

    T createInstance(JsonObject pJson, DeserializationContext pContext);

    void runPacket();

}
