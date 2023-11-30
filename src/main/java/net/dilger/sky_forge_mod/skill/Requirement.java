package net.dilger.sky_forge_mod.skill;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.C2SRemovePlayerXpPacket;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.concurrent.atomic.AtomicBoolean;

public class Requirement {

    private final SKILL_TYPE skillType;
    private final int skillLevel;
    private final int xpCost;
    private final Item item;
    private final int itemCost;


    public Requirement(SKILL_TYPE skill_type, int skillLevel) {
        this(skill_type, skillLevel, 0, null, 0);
    }

    public Requirement(SKILL_TYPE skill_type, int skillLevel, int xpCost) {
        this(skill_type, skillLevel, xpCost, null, 0);
    }

    public Requirement(SKILL_TYPE skill_type, int skillLevel, Item item, int itemCost) {
        this(skill_type, skillLevel, 0, item, itemCost);
    }
    public Requirement(SKILL_TYPE skill_type, int skillLevel, int xpCost, Item item, int itemCost) {
        this.skillType = skill_type;
        this.skillLevel = skillLevel;
        this.xpCost = xpCost;
        this.item = item;
        this.itemCost = itemCost;
    }



    public boolean meetsRequirements(ServerPlayer player) {
        AtomicBoolean flag = new AtomicBoolean(true);
        // check skill level
        player.getCapability(PlayerSkillXpProvider.PLAYER_SKILL_XP).ifPresent(skill -> {
            if (skill.getSkillLevel(this.skillType) < skillLevel) {
                flag.set(false);
            }
        });
        // check player xp
        if (player.experienceLevel < xpCost) {
            flag.set(false);
        }
        // check items
        if (player.getInventory().countItem(item) < itemCost) {
            flag.set(false);
        }

        return flag.get();
    }

    public void payRequirements(ServerPlayer player) {
        // remove player xp
        PacketHandling.sentToServer(new C2SRemovePlayerXpPacket((byte) 1));
        // remove items
        int itemsRemoved = 0;
        while (itemsRemoved < itemCost) {
            int slotWithItem = player.getInventory().findSlotMatchingItem(new ItemStack(item));
            int slotItemCount = player.getInventory().getItem(slotWithItem).getCount();
            if (slotItemCount < itemCost) {
                player.getInventory().removeItem(slotWithItem, slotItemCount);
                itemsRemoved += slotItemCount;
            }
            else{
                player.getInventory().removeItem(slotWithItem, itemCost);
                itemsRemoved += itemCost;
            }
        }

    }


    public JsonElement serializeToJson() {

        JsonObject jsonobject = new JsonObject();

        jsonobject.addProperty("skill_type", this.skillType.name());
        jsonobject.addProperty("skill_level", this.skillLevel);
        if (this.xpCost != 0) {
            jsonobject.addProperty("xp_cost", this.xpCost);
        }
        if (this.itemCost != 0 && this.item != null) {

            jsonobject.addProperty("item", Item.getId(this.item));
            jsonobject.addProperty("item_cost", this.itemCost);
        }

        return jsonobject;
    }

    public static Requirement fromJson(JsonObject pJson, DeserializationContext pContext) {
        SKILL_TYPE skillType = SKILL_TYPE.valueOf(GsonHelper.getAsString(pJson, "skill_type"));
        int skillLevel = GsonHelper.getAsInt(pJson, "skill_level");

        int xpCost = pJson.has("xp_cost") ? GsonHelper.getAsInt(pJson, "xp_cost") : 0;
        Item item = pJson.has("item") ? Item.byId(GsonHelper.getAsInt(pJson, "item")) : null;
        int itemCost = pJson.has("item_cost") ? GsonHelper.getAsInt(pJson, "item_cost") : 0;
        return new Requirement(skillType, skillLevel, xpCost, item, itemCost);

    }
}
