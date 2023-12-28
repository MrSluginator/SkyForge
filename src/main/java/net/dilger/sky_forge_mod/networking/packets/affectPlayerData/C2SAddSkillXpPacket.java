package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.skills.PlayerSkillXpProvider;
import net.dilger.sky_forge_mod.skill.SKILL_TYPE;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SAddSkillXpPacket {

    //this is what is getting encoded / decoded
    //add all xp variables here??
    private static final String MESSAGE_GAIN_XP = "message.sky_forge_mod.gain_xp";
    private final SKILL_TYPE skill_type;

    private final byte amount;

    //add variables that you want to be passed to the constructor below
    public C2SAddSkillXpPacket(SKILL_TYPE skill, byte amount){
        this.skill_type = skill;
        this.amount = amount;
    }
    public C2SAddSkillXpPacket(FriendlyByteBuf buffer) {
        this(SKILL_TYPE.valueOf(buffer.readUtf()), buffer.readByte());
    }

    //you need one buffer.write per variable you are passing to the constructor
    public void encode(FriendlyByteBuf buffer) {

        buffer.writeUtf(this.skill_type.toString());
        buffer.writeByte(this.amount);

    }

    //this is the logic for what we want to do when this message is built
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            ServerPlayer player = context.getSender();

            // Notify the player that they have gained a perk
            assert player != null;

            // Change the player skill xp

            player.getCapability(PlayerSkillXpProvider.PLAYER_SKILL_XP).ifPresent(skill_xp -> {
            skill_xp.addSkillXp(amount, skill_type);

            player.sendSystemMessage(Component.literal(skill_type.toString() + " xp: " + skill_xp.getSkillXp(skill_type)).withStyle(ChatFormatting.GOLD));
            player.sendSystemMessage(Component.literal(String.valueOf((skill_type))).withStyle(ChatFormatting.BLACK));
            PacketHandling.sentToPlayer(new S2CSyncSkillXpPacket(skill_xp.getSkillsXpMap(), skill_type), player);
            });

            // TODO: Check if the player has leveled up and or met xp requirements

        });
        return true;
    }
}
