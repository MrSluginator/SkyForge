package net.dilger.sky_forge_mod.util;

import net.dilger.sky_forge_mod.skills.PlayerSkillData;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataUtility {
    private static final Map<String, PlayerSkillData> playerData = new HashMap<>();

    public static PlayerSkillData getPlayerData(Player pPlayer){
        if (!playerData.containsKey(pPlayer.getUUID().toString())) {
            PlayerSkillData data = new PlayerSkillData();
            playerData.put(pPlayer.getUUID().toString(), data);
            return data;
        }
        return playerData.get(pPlayer.getUUID().toString());
    }

    public static void setPlayerData(Player pPlayer, PlayerSkillData data) {
        if (data == null) playerData.remove(pPlayer.getUUID().toString());
        else playerData.put(pPlayer.getUUID().toString(), data);
    }


}
