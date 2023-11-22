package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.client.ClientSkillXpData;
import net.dilger.sky_forge_mod.skills.PlayerSkillXp;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class S2CSyncSkillXpPacket {
    private final long skill_xp;
    private final PlayerSkillXp.SKILL_TYPE skill_type;

    public S2CSyncSkillXpPacket(Map<PlayerSkillXp.SKILL_TYPE, Long> skillXpMap, PlayerSkillXp.SKILL_TYPE skill_type) {
        // get xp from the map that was passed in
        this.skill_type = skill_type;
        this.skill_xp = skillXpMap.get(skill_type);


    }

    public S2CSyncSkillXpPacket(FriendlyByteBuf buf) {

        // read xp long from file
        this.skill_type = PlayerSkillXp.SKILL_TYPE.valueOf(buf.readUtf());
        this.skill_xp = buf.readLong();

    }

    public void encode(FriendlyByteBuf buf) {

        // write xp long to file
        buf.writeUtf(skill_type.toString());
        buf.writeLong(skill_xp);

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE CLIENT
            // set xp on player capability
            ClientSkillXpData.setSkill_xp(skill_xp, skill_type);

        });
        return true;
    }
}
