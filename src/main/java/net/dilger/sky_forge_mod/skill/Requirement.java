package net.dilger.sky_forge_mod.skill;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.dilger.sky_forge_mod.networking.PacketHandling;
import net.dilger.sky_forge_mod.networking.packets.affectPlayerData.C2SPayRequirementsPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;

public class Requirement {

    private final SKILL_TYPE skillType;
    private final int skillLevel;
    private final int xpCost;
    private final Item item;
    private final int itemCost;
    private boolean requirementsMet = false;


    public Requirement(SKILL_TYPE skill_type, int skillLevel, int xpCost, Item item, int itemCost) {
        this.skillType = skill_type;
        this.skillLevel = skillLevel;
        this.xpCost = xpCost;
        this.item = item;
        this.itemCost = itemCost;
    }



    public boolean meetsRequirements(ServerPlayer player) {

        // Notify the player that they have gained a perk
        assert player != null;

        // Change the player skill xp
        // local player does not have capability
        player.getCapability(PlayerSkillXpProvider.PLAYER_SKILL_XP).ifPresent(skill_xp -> {
            skill_xp.getSkillLevel(getSkillType());

            boolean flag = true;
            // check skill level

            if (skill_xp.getSkillLevel(getSkillType()) < getSkillLevel()) {
                flag = false;
                player.sendSystemMessage(Component.literal("skill level : " + skill_xp.getSkillLevel(getSkillType())));
            }

            // check player xp
            System.out.println("xp lvl : " + player.experienceLevel + " xp cost : " + getXpCost());
            if (player.experienceLevel < getXpCost()) {
                flag = false;
                player.sendSystemMessage(Component.literal("xp level"));

            }
            // check items
            if (player.getInventory().countItem(getItem()) < getItemCost()) {
                flag = false;
                player.sendSystemMessage(Component.literal("item count"));

            }

            System.out.println(flag);
            System.out.println("handle : " + this);
            requirementsMet(flag);

        });

        return requirementsMet;
    }

    public void payRequirements() {

        PacketHandling.sentToServer(new C2SPayRequirementsPacket(this));

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

    public static Requirement fromJson(JsonObject pJson) {
        SKILL_TYPE skillType = SKILL_TYPE.byName(GsonHelper.getAsString(pJson, "skill_type"));
        int skillLevel = GsonHelper.getAsInt(pJson, "skill_level");

        int xpCost = pJson.has("xp_cost") ? GsonHelper.getAsInt(pJson, "xp_cost") : 0;
        Item item = pJson.has("item") ? Item.byId(GsonHelper.getAsInt(pJson, "item")) : null;
        int itemCost = pJson.has("item_cost") ? GsonHelper.getAsInt(pJson, "item_cost") : 0;
        return new Requirement(skillType, skillLevel, xpCost, item, itemCost);

    }

    public SKILL_TYPE getSkillType() {
        return skillType;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getXpCost() {
        return xpCost;
    }

    public Item getItem() {
        return item;
    }

    public int getItemCost() {
        return itemCost;
    }

    public void requirementsMet(boolean met) {
        requirementsMet = met;
    }
}
