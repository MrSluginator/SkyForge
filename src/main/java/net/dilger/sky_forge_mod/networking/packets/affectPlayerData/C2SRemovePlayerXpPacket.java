package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SRemovePlayerXpPacket {

    //this is what is getting encoded / decoded
    //add all xp variables here??
    private final byte amount;

    //add variables that you want to be passed to the constructor below
    public C2SRemovePlayerXpPacket(byte amount){
        this.amount = amount;
    }
    public C2SRemovePlayerXpPacket(FriendlyByteBuf buffer) {
        this(buffer.readByte());
    }


    //you need one buffer.write per variable you are passing to the constructor
    public void encode(FriendlyByteBuf buffer) {

        buffer.writeByte(this.amount);

    }

    //this is the logic for what we want to do when this message is built
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            ServerPlayer player = context.getSender();
//            ServerLevel level = player.serverLevel();

            // Notify the player that they have gained a perk
            assert player != null;

            // Change the player skill xp

            player.experienceLevel -= amount;

        });
        return true;
    }

}
