package net.dilger.sky_forge_mod.networking.packets.affectPlayerData;

import net.dilger.sky_forge_mod.skill.Perk;
import net.dilger.sky_forge_mod.skill.PerkList;
import net.dilger.sky_forge_mod.skill.player.PlayerPerkCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SUpdatePlayerPerksPacket {

    public C2SUpdatePlayerPerksPacket() {}

    public C2SUpdatePlayerPerksPacket(FriendlyByteBuf buf) {}

    public void encode(FriendlyByteBuf buffer) {}

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE IS ON THE SERVER
            ServerPlayer player = context.getSender();

            player.getCapability(PlayerPerkCapability.PLAYER_PERKS).ifPresent(playerPerks -> {

                for (byte perkID: playerPerks.getPerks()) {
                    Perk perk = PerkList.get(perkID);
                    System.out.println(perk.isActive() + " : " + perk.getResourceLocation());

                    if (!perk.isActive()) {
                        System.out.println("Activating Perk : " + perk.getResourceLocation());
                        perk.getReward().grantRewards();
                        perk.setActive(true);
                    }
                }

            });
        });
        return true;
    }


}
