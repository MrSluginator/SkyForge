package net.dilger.sky_forge_mod.gui.screen.skill;

import com.google.common.collect.Sets;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.IconInfo;
import net.dilger.sky_forge_mod.gui.screen.skill.buttons.PerkButton;
import net.dilger.sky_forge_mod.skill.Perk;
import net.dilger.sky_forge_mod.skill.PerkDisplayInfo;
import net.dilger.sky_forge_mod.skill.PlayerSkillXp;
import net.dilger.sky_forge_mod.util.KeyBinding;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class SkillTreeScreen extends Screen {


    private static final Component TITLE =
            Component.translatable("gui." + SkyForgeMod.MOD_ID + ".skill_tree_screen");
    private final Perk root;
//    private final PerkButton rootButton;
    private final int imageWidth, imageHeight;
    private int leftPos, topPos;

    private PlayerSkillXp.SKILL_TYPE skill_type;
    public SkillTreeScreen(PlayerSkillXp.SKILL_TYPE skill_type) {
        super(TITLE);

        this.imageWidth = 176;
        this.imageHeight = 166;

        // testing
        // need to find a better way to add buttons to the skill screen
        this.skill_type = skill_type;
        PerkDisplayInfo rootDisplay = new PerkDisplayInfo(Component.literal("root"), null);
        this.root = new Perk(null, IconInfo.HEART_PLUS);
        Perk child = new Perk(this.root, IconInfo.HEART);
        this.root.addChild(child);
        Perk grandChild = new Perk(child, IconInfo.HEART_DOUBLE);
        child.addChild(grandChild);

    }

    @Override
    protected void init() {
        super.init();

        this.leftPos = (this.width) / 2;
        this.topPos = (this.height) / 2;

        if(this.minecraft == null) return;
        Level level = this.minecraft.level;
        if(level == null) return;

        //create buttons
        for (PerkButton perkButton: getAllButtons(root)) {
            // adding a widget puts it into the build in child set
            addRenderableWidget(perkButton);
        }
    }

    private Set<PerkButton> getAllButtons(Perk perk) {
        Set<PerkButton> buttons = Sets.newHashSet(perk.getButton());

        for (Perk child: perk.getChildren()) {
            buttons.add(child.getButton());
            getAllButtons(buttons, child);
        }

        return buttons;
    }

    private void getAllButtons(Set<PerkButton> buttons, Perk perk) {
        for (Perk child: perk.getChildren()) {
            buttons.add(child.getButton());
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // darkens the background screen
        this.renderBackground(graphics);
//        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
//        this.drawContents(graphics, this.leftPos, this.topPos);
        this.renderPerks(graphics, mouseX, mouseY, partialTicks);
        //this is the format of how we draw text on the exampleScreen
        graphics.drawString(this.font,
                TITLE,
                this.leftPos + 8,
                this.topPos + 8,
                0x404040,
                false);
    }

    public void renderPerks(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        this.root.drawConnectivity(graphics, 0, 0);
        //super.render renders all widgets
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.root.drawIcon(graphics, 0, 0);
    }
    public void drawContents(GuiGraphics pGuiGraphics, int pX, int pY) {
        /*if (!this.centered) {
            this.scrollX = (double)(117 - (this.maxX + this.minX) / 2);
            this.scrollY = (double)(56 - (this.maxY + this.minY) / 2);
            this.centered = true;
        }*/

        pGuiGraphics.enableScissor(pX, pY, pX + 234, pY + 113);
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float)pX, (float)pY, 0.0F);
//        background resource
//        ResourceLocation resourcelocation = Objects.requireNonNullElse(this.displayInfo.getBackground(), TextureManager.INTENTIONAL_MISSING_TEXTURE);
//        int i = Mth.floor(this.scrollX);
//        int j = Mth.floor(this.scrollY);
//        int k = i % 16;
//        int l = j % 16;
//
//        for(int i1 = -1; i1 <= 15; ++i1) {
//            for(int j1 = -1; j1 <= 8; ++j1) {
//                pGuiGraphics.blit(resourcelocation, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
//            }
//        }

        /*this.rootButton.drawConnectivity(pGuiGraphics, 0, 0, true);
        this.rootButton.drawConnectivity(pGuiGraphics, 0, 0, false);
        this.rootButton.draw(pGuiGraphics, 0, 0);
        pGuiGraphics.pose().popPose();
        pGuiGraphics.disableScissor();*/
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

//        atm it just closes the screen but could be used to switch tabs etc
        if (KeyBinding.OPEN_TESTSCREEN.matches(pKeyCode, pScanCode)) {
            this.minecraft.setScreen(null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
