package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.C2SUpdatePlayerPerksPacket;
import net.dilger.sky_forge_mod.skill.common_buff.CommonBuffs;
import net.dilger.sky_forge_mod.skill.unique_buff.UniqueBuffs;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import java.util.Map;
import java.util.Set;

public class PerkList {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static byte PERK_ID = 0;
    private static final Map<ResourceLocation, Perk> perks = Maps.newHashMap();
    private static final Set<Perk> roots = Sets.newLinkedHashSet();
    private static final Set<Perk> buffs = Sets.newLinkedHashSet();

    public static void init() {
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.DEFENSE));
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.FARMING));
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.FISHING));
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.MINING));
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.MOBILITY));
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.OFFENSE));
        Perk.Builder.fromResourceLocation(Perk.resourceFromName("root", SKILL_TYPE.TRADING));

        CommonBuffs.all();
        UniqueBuffs.all();
    }
    public static void add(Perk perk) {
        if (!perks.containsKey(perk.getResourceLocation())) {
            perk.setID(PERK_ID++);
        }
        else {
            perk.setID(perks.get(perk.getResourceLocation()).getID());
        }
        perks.put(perk.getResourceLocation(), perk);

        System.out.println(perks.keySet());

        if (perk.getParent() == null) {
            roots.add(perk);
        }
        else {
            buffs.add(perk);
        }
    }

    public static Perk get(byte perkID) {
        for (Perk perk: perks.values()) {
            if (perk.getID() == perkID) {
                return perk;
            }
        }
        LOGGER.warn("Couldn't get perk with ID {}", perkID);
        return null;
    }

    public static Perk get(ResourceLocation resourceLocation) {
        if (perks.containsKey(resourceLocation)) {
            return perks.get(resourceLocation);
        }
        else {
            LOGGER.warn("Couldn't get perk {}", resourceLocation);
            return null;
        }
    }

    public Map<ResourceLocation, Perk> getPerks() {
        return perks;
    }

    public Set<Perk> getRoots() {
        return roots;
    }

    public Set<Perk> getBuffs() {
        return buffs;
    }

    public static void updatePlayerPerks() {
        PacketHandling.sentToServer(new C2SUpdatePlayerPerksPacket());
    }
}
