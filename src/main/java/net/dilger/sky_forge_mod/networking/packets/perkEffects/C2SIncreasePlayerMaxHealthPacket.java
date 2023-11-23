package net.dilger.sky_forge_mod.networking.packets.perkEffects;

import net.dilger.sky_forge_mod.util.ModifierHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SIncreasePlayerMaxHealthPacket {

    private final byte amount;

    /**
     * parameters must be set in both constructors through overflow
     * @param amount the amount that the stat will increase by
     */
    public C2SIncreasePlayerMaxHealthPacket(byte amount) {
        this.amount = amount;
    }

    public C2SIncreasePlayerMaxHealthPacket(FriendlyByteBuf buf) {
       this(buf.readByte());
    }

    /**
     * this writes to the .json file
     * used in S2C packets
     *
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

            // set MAX HEALTH by using the ModifierHandler Class
            // addition adds some amount to the base number of hearts (20) i.e. amount = 2 -> 20 + 2 = 22 hearts
            // you need to get the current total then subtract the base number of hearts
            ModifierHandler.setMaxHealth(player, player.getMaxHealth() - 20 + (float) amount, AttributeModifier.Operation.ADDITION);

            player.sendSystemMessage(Component.literal("HEARTS GAINED: " + amount)
                    .withStyle(ChatFormatting.RED));
            player.sendSystemMessage(Component.literal("TOTAL HEARTS: " + player.getMaxHealth()));
        });
        return true;
    }

}
