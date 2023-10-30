package net.dilger.sky_forge_mod.client;

public class ClientSkillXpData {

    private static long offensive_xp;
    private static long defensive_xp;
    private static long mining_xp;
    private static long farming_xp;
    private static long trading_xp;
    private static long fishing_xp;
    private static long mobility_xp;

    public static long getOffensive_xp() {
        return offensive_xp;
    }

    public static void setOffensive_xp(long offensive_xp) {
        ClientSkillXpData.offensive_xp = offensive_xp;
    }

    public static long getDefensive_xp() {
        return defensive_xp;
    }

    public static void setDefensive_xp(long defensive_xp) {
        ClientSkillXpData.defensive_xp = defensive_xp;
    }

    public static long getMining_xp() {
        return mining_xp;
    }

    public static void setMining_xp(long mining_xp) {
        ClientSkillXpData.mining_xp = mining_xp;
    }

    public static long getFarming_xp() {
        return farming_xp;
    }

    public static void setFarming_xp(long farming_xp) {
        ClientSkillXpData.farming_xp = farming_xp;
    }

    public static long getTrading_xp() {
        return trading_xp;
    }

    public static void setTrading_xp(long trading_xp) {
        ClientSkillXpData.trading_xp = trading_xp;
    }

    public static long getFishing_xp() {
        return fishing_xp;
    }

    public static void setFishing_xp(long fishing_xp) {
        ClientSkillXpData.fishing_xp = fishing_xp;
    }

    public static long getMobility_xp() {
        return mobility_xp;
    }

    public static void setMobility_xp(long mobility_xp) {
        ClientSkillXpData.mobility_xp = mobility_xp;
    }

}
