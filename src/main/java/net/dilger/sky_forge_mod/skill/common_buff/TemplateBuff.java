package net.dilger.sky_forge_mod.skill.common_buff;

import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.skill.Buff;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;

public class TemplateBuff implements Buff {
    static final ResourceLocation PACKET = new ResourceLocation("template");
    @Override
    public ResourceLocation getPacketResourceLocation() {
        return PACKET;
    }

    @Override
    public Object createInstance(JsonObject pJson, DeserializationContext pContext) {
        return null;
    }

    @Override
    public void runPacket() {

    }
}
