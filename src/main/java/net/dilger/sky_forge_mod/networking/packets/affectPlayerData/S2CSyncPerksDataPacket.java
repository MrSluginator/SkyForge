package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.client.ClientTalentData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncPerksDataPacket {
    private final byte[] perks;

    public S2CSyncPerksDataPacket(byte[] incomingPerks) {
        this.perks = incomingPerks;
    }

    public S2CSyncPerksDataPacket(FriendlyByteBuf buf) {

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
