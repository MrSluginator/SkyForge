package net.dilger.sky_forge_mod.skills;

public class PlayerSkillData {
    private long offensive_xp;
    private long defensive_xp;
    private long mining_xp;
    private long farming_xp;
    private long trading_xp;
    private long fishing_xp;
    private long mobility_xp;

    public long getOffensive_xp() {
        return offensive_xp;
    }

    public void setOffensive_xp(long offensive_xp) {
        this.offensive_xp = offensive_xp;
    }

    public void addOffensive_xp(long offensive_xp) {
        this.offensive_xp += offensive_xp;
    }

    public long getDefensive_xp() {
        return defensive_xp;
    }

    public void setDefensive_xp(long defensive_xp) {
        this.defensive_xp = defensive_xp;
    }

    public void addDefensive_xp(long defensive_xp) {
        this.defensive_xp += defensive_xp;
    }

    public long getMining_xp() {
        return mining_xp;
    }

    public void setMining_xp(long mining_xp) {
        this.mining_xp = mining_xp;
    }

    public void addMining_xp(long mining_xp) {
        this.mining_xp += mining_xp;
    }

    public long getFarming_xp() {
        return farming_xp;
    }

    public void setFarming_xp(long farming_xp) {
        this.farming_xp = farming_xp;
    }

    public void addFarming_xp(long farming_xp) {
        this.farming_xp += farming_xp;
    }

    public long getTrading_xp() {
        return trading_xp;
    }

    public void setTrading_xp(long trading_xp) {
        this.trading_xp = trading_xp;
    }

    public void addTrading_xp(long trading_xp) {
        this.trading_xp += trading_xp;
    }

    public long getFishing_xp() {
        return fishing_xp;
    }

    public void setFishing_xp(long fishing_xp) {
        this.fishing_xp = fishing_xp;
    }

    public void addFishing_xp(long fishing_xp) {
        this.fishing_xp += fishing_xp;
    }

    public long getMobility_xp() {
        return mobility_xp;
    }

    public void setMobility_xp(long mobility_xp) {
        this.mobility_xp = mobility_xp;
    }

    public void addMobility_xp(long mobility_xp) {
        this.mobility_xp += mobility_xp;
    }
}
