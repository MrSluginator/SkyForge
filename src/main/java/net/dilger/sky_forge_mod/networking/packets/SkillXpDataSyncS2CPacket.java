package net.dilger.sky_forge_mod.networking.packets;

import net.dilger.sky_forge_mod.client.ClientSkillXpData;
import net.dilger.sky_forge_mod.skill.PlayerSkillXp;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class SkillXpDataSyncS2CPacket {
    private final long offensive_xp;
    private final long defensive_xp;
    private final long mining_xp;
    private final long farming_xp;
    private final long trading_xp;
    private final long fishing_xp;
    private final long mobility_xp;

    public SkillXpDataSyncS2CPacket(Map<PlayerSkillXp.SKILL_TYPE, Long> skillXpMap) {
        this.offensive_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.OFFENSE);
        this.defensive_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.DEFENSE);
        this.mining_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.MINING);
        this.farming_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.FARMING);
        this.trading_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.TRADING);
        this.fishing_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.FISHING);
        this.mobility_xp = skillXpMap.get(PlayerSkillXp.SKILL_TYPE.MOBILITY);

    }

    public SkillXpDataSyncS2CPacket(FriendlyByteBuf buf) {

        this.offensive_xp = buf.readLong();
        this.defensive_xp = buf.readLong();
        this.mining_xp = buf.readLong();
        this.farming_xp = buf.readLong();
        this.trading_xp = buf.readLong();
        this.fishing_xp = buf.readLong();
        this.mobility_xp = buf.readLong();
    }

    public void toBytes(FriendlyByteBuf buf) {

        buf.writeLong(offensive_xp);
        buf.writeLong(defensive_xp);
        buf.writeLong(mining_xp);
        buf.writeLong(farming_xp);
        buf.writeLong(trading_xp);
        buf.writeLong(fishing_xp);
        buf.writeLong(mobility_xp);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE CLIENT
            ClientSkillXpData.setOffensive_xp(offensive_xp);
            ClientSkillXpData.setDefensive_xp(defensive_xp);
            ClientSkillXpData.setMining_xp(mining_xp);
            ClientSkillXpData.setFarming_xp(farming_xp);
            ClientSkillXpData.setTrading_xp(trading_xp);
            ClientSkillXpData.setFishing_xp(fishing_xp);
            ClientSkillXpData.setMobility_xp(mobility_xp);
        });
        return true;
    }
}
