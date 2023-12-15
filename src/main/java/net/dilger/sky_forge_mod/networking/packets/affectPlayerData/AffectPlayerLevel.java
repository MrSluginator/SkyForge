package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.skills.PlayerSkillXp;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.network.NetworkEvent;

import net.dilger.sky_forge_mod.util.ModifierHandler;

import java.util.function.Supplier;

public class AffectPlayerLevel{

    private byte amount = 0;

    //ANY TIME AffectPlayerLevel() is called all of the below methods happen
    //any variables that you want to be passed through must be declared above

    public AffectPlayerLevel(byte amount) {
        assert Minecraft.getInstance().player != null;
        Minecraft.getInstance().player.sendSystemMessage(Component.literal(String.valueOf(amount)));
        this.amount = amount;
    }

    public AffectPlayerLevel(FriendlyByteBuf buf) {
        this(buf.readByte());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeByte(this.amount);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            System.out.println("HANDLE"+this);
            ServerPlayer player = context.getSender();

            assert player != null;
            player.experienceLevel += amount;
            // add ABSORPTION
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100000, 6));
            //set MAX HEALTH by using the ModifierHandler Class
            ModifierHandler.setMaxHealth(player, 25, AttributeModifier.Operation.ADDITION);
            //MobEffectInstance is used for testing what effects an entity already has
            //this gives custom effect to player. this is the format for future reference

            player.sendSystemMessage(Component.literal("IT WORKS LOL")
                    .withStyle(ChatFormatting.GOLD));
        });
        return true;
    }


}
