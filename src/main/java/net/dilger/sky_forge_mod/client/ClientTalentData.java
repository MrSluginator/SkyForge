package net.dilger.sky_forge_mod.client;

public class ClientTalentData {

    private static byte[] talents;

    public static byte[] getTalents() {
        return talents;
    }

    public static void setTalents(byte[] newTalents) {
        ClientTalentData.talents = newTalents;
    }
}


