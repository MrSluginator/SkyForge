package net.dilger.sky_forge_mod.skill.common_buff;

import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.perkEffects.C2SIncreasePlayerMaxHealthPacket;
import net.dilger.sky_forge_mod.skill.Buff;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;

public class MaxHealthBuff implements Buff {
    static final ResourceLocation PACKET = new ResourceLocation("max_health");
    private final byte amount;

    public MaxHealthBuff(byte amount) {
        this.amount = amount;
    }

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
        PacketHandling.sentToServer(new C2SIncreasePlayerMaxHealthPacket(amount));
    }
}
