package net.dilger.sky_forge_mod.networking.packets.affectClientData;

import net.dilger.sky_forge_mod.client.ClientSkillXpData;
import net.dilger.sky_forge_mod.client.ClientTalentData;
import net.dilger.sky_forge_mod.skills.PlayerSkillXp;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.Array;
import java.util.Map;
import java.util.function.Supplier;

public class PlayerPerksS2CPacket {
    private byte[] perks = new byte[]{(byte) 111};

    public PlayerPerksS2CPacket(byte[] incomingPerks) {
        this.perks = incomingPerks;

    }

    public PlayerPerksS2CPacket(FriendlyByteBuf buf) {

        this.perks = buf.readByteArray();
    }

    public void toBytes(FriendlyByteBuf buf) {

        buf.writeBytes(perks);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE CLIENT
            ClientTalentData.setTalents(perks);

        });
        return true;
    }
}
