package net.dilger.sky_forge_mod.networking;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.player.Player;

//The goal of this class is to change player attributes and have them save to the server duh
//
public class ChangePlayerAtributes extends ServerPlayer {

    private MinecraftServer pServer;// = this.getServer();

    private Player pPlayer = Minecraft.getInstance().player;

    private GameProfile profile;// = this.getGameProfile();

    private ServerLevel pServerLevel;// = this.pServer.getLevel(pPlayer);

    public ChangePlayerAtributes(MinecraftServer pServer, GameProfile pGameProfile){
        super(pServer, pServer.getLevel(Minecraft.getInstance().player.level().dimension()), pGameProfile);
        this.pServer = pServer;
        this.pServerLevel = pServer.getLevel(Minecraft.getInstance().player.level().dimension());
        this.profile = pGameProfile;

    }

    public void subtractLevel(int amount){
        this.setExperienceLevels(Minecraft.getInstance().player.experienceLevel - amount);
    }

    public void addLevel(int amount){
        this.setExperienceLevels(Minecraft.getInstance().player.experienceLevel + amount);
    }
}
