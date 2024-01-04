package net.dilger.sky_forge_mod.skill.common_buff;

import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.perkEffects.C2SIncreasePlayerResistancePacket;
import net.dilger.sky_forge_mod.skill.Buff;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;

public class ResistanceBuff implements Buff {
    static final ResourceLocation PACKET = new ResourceLocation(SkyForgeMod.MOD_ID,"resistance");

    private final byte amount;

    public ResistanceBuff(byte amount) {
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
        System.out.println("Run Packet");
        PacketHandling.sentToServer(new C2SIncreasePlayerResistancePacket(amount));
    }
}
