package net.dilger.sky_forge_mod.skill;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.dilger.sky_forge_mod.gui.screen.skill.IconType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkFrameType;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.RarityType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class PerkDisplayInfo {
    private static final int minXSeparation = 32;
    private static final int minYSeparation = 32;
    private final Component name;
    private final Component description;
    private final PerkFrameType frame;
    private final RarityType rarity;
    @Nullable
    private final IconType icon;
    private int rX, rY;

    public PerkDisplayInfo(Component name, Component description, PerkFrameType frame, RarityType rarity, @Nullable IconType icon) {
        this.name = name;
        this.description = description;
        this.frame = frame;
        this.rarity = rarity;
        this.icon = icon;
    }

    public void setTreeLocation(int iX, int iY) {
        this.rX = Mth.floor(iX) * minXSeparation;;
        this.rY = Mth.floor(iY) * minYSeparation;;
    }

    public Component getName() {
        return name;
    }

    public Component getDescription() {
        return description;
    }

    public PerkFrameType getFrame() {
        return frame;
    }

    public RarityType getRarity() {
        return rarity;
    }

    @Nullable
    public IconType getIcon() {
        return icon;
    }

    public int getTreeX() {
        return rX;
    }

    public int getTreeY() {
        return rY;
    }

    public static PerkDisplayInfo fromJson(JsonObject pJson) {
        Component name = Component.Serializer.fromJson(pJson.get("name"));
        Component description = Component.Serializer.fromJson(pJson.get("description"));
        if (name != null && description != null) {
            IconType icon = pJson.has("icon") ? IconType.byName(GsonHelper.getAsString(pJson, "icon")) : IconType.HEART;
            PerkFrameType frame = pJson.has("frame") ? PerkFrameType.byName(GsonHelper.getAsString(pJson, "frame")) : PerkFrameType.SQUARE;
            RarityType rarity = pJson.has("rarity") ? RarityType.byName(GsonHelper.getAsString(pJson, "rarity")) : RarityType.COMMON;
            return new PerkDisplayInfo(name, description, frame, rarity, icon);
        } else {
            throw new JsonSyntaxException("Both title and description must be set");
        }
    }

    public JsonElement serializeToJson() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.add("name", Component.Serializer.toJsonTree(this.name));
        jsonobject.add("description", Component.Serializer.toJsonTree(this.description));
        jsonobject.addProperty("frame", this.frame.getName().toString());
        jsonobject.addProperty("rarity", this.rarity.getName().toString());
        jsonobject.addProperty("icon", icon == null ? null : this.icon.getName().toString());


        return jsonobject;
    }

}
