package net.dilger.sky_forge_mod.networking.packets.perkEffects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class C2SIncreasePlayerFireResistancePacket {

    private final byte amount;

    /**
     * parameters must be set in both constructors through overflow
     * @param amount the amount that the stat will increase by
     */
    public C2SIncreasePlayerFireResistancePacket(byte amount) {
        this.amount = amount;
    }

    public C2SIncreasePlayerFireResistancePacket(FriendlyByteBuf buf) {
       this(buf.readByte());
    }

    /**
     * this writes to the .json file
     * used in S2C packets
     */
    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(amount);
    }

    /**
     * contains the logic for what happens server-side when the packet is called
     */
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            ServerPlayer player = context.getSender();

            assert player != null;

            // checks to see if player already has this effect then adds on the amount onto what they already have
            int totalAmplification = (player.hasEffect(MobEffects.FIRE_RESISTANCE)) ? amount + Objects.requireNonNull(player.getEffect(MobEffects.FIRE_RESISTANCE)).getAmplifier() : amount;

            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, MobEffectInstance.INFINITE_DURATION, totalAmplification));
        });
        return true;
    }

}
