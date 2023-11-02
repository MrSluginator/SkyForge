package net.dilger.sky_forge_mod.gui.screen.skill.buttons;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.skill.Perk;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class PerkButton extends ImageButton {

    private final Perk perk;
    private final List<PerkButton> childrenButtons = Lists.newArrayList();
    private static final ResourceLocation BASE_TEXTURE = new ResourceLocation(SkyForgeMod.MOD_ID, "textures/gui/skill/perk_buttons_base.png");
    private static final int TEXTURE_WIDTH = 200;
    private static final int TEXTURE_HEIGHT = 26;
    private static final int BUTTON_WIDTH = 26;
    private static final int BUTTON_HEIGHT = 26;


    /*
    * screen position is denoted with prefix p
    * relative position is denoted with prefix r
    * texture is prefix t
    */
    public PerkButton(Perk perk, int pX, int pY, int texStartX, int texStartY, OnPress onPress) {
        super(pX + 3, pY, BUTTON_WIDTH, BUTTON_HEIGHT, texStartX, 128 + texStartY, BASE_TEXTURE, onPress);
        // default texture size is 256x256px
        this.perk = perk;

    }

    public int getWidth() {
        return BUTTON_WIDTH;
    }

    public int getHeight() {
        return BUTTON_HEIGHT;
    }

}
