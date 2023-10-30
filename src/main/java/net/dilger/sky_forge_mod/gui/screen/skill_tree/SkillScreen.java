package net.dilger.sky_forge_mod.gui.screen.skill_tree;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.dilger.sky_forge_mod.SkyForgeMod;
import net.dilger.sky_forge_mod.client.KeyBinding;
import net.dilger.sky_forge_mod.gui.buttons.ExampleButton;
import net.dilger.sky_forge_mod.skills.Perk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

/*
The skill tree screen encapsulates the entire screen that gets rendered
It's essential the parent screen to all other screen in the skill tree menu

Skills are of several types each with their own tab and screen
*/

public class SkillScreen extends Screen {
    private static final ResourceLocation WINDOW_RESOURCE_LOCATION = new ResourceLocation(SkyForgeMod.MOD_ID,"textures/gui/skills/window.png");

    private static final Component VERY_SAD_LABEL = Component.translatable("skills.sad_label");
    private static final Component NO_PERKS_LABEL = Component.translatable("skills.empty");
    private final Map<Perk, SkillTreeScreen> skillTrees = Maps.newLinkedHashMap();
    private SkillTreeScreen selectedSkillTree;
    private boolean isScrolling;
    private static int tabPage, maxPages;
    private static ExampleButton exButton;

    public SkillScreen() {
//    constructor
        super(Component.literal("Skill Tree Screen"));
        // should implement client skills: the skills that are saved onto the player
    }

    protected void init() {
        tabPage = 0;
        exButton = new ExampleButton();
        /*
        initialized the selected skill tree as well as the skillTrees to pick other ones
        modifies the page if there are too many skillTrees at the top by adding arrow buttons

        this.skillTrees.clear();
        this.selectedTab = null;
        this.perks.setListener(this);
        if (this.selectedTab == null && !this.skillTrees.isEmpty()) {
            this.perks.setSelectedTab(this.skillTrees.values().iterator().next().getPerk(), true);
        } else {
            this.perks.setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getPerk(), true);
        }
        if (this.skillTrees.size() > SkillTreeScreenType.MAX_SKILLTREES) {
            int guiLeft = (this.width - 252) / 2;
            int guiTop = (this.height - 140) / 2;
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal("<"), b -> tabPage = java.lang.Math.max(tabPage - 1, 0       ))
                    .pos(guiLeft, guiTop - 50).size(20, 20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal(">"), b -> tabPage = Math.min(tabPage + 1, maxPages))
                    .pos(guiLeft + WINDOW_WIDTH - 20, guiTop - 50).size(20, 20).build());
            maxPages = this.skillTrees.size() / SkillTreeScreenType.MAX_SKILLTREES;
        }*/
    }

    public void removed() {
        /*
        send signal to mc that this screen has been closed

        this.perks.setListener((ClientPerks.Listener)null);
        ClientPacketListener clientpacketlistener = this.minecraft.getConnection();
        if (clientpacketlistener != null) {
            clientpacketlistener.send(ServerboundSeenPerksPacket.closedScreen());
        }*/

    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        /*
        check if any of the skillTrees have been clicked
        change the selected tab if there has

        if (pButton == 0) {
            int i = (this.width - 252) / 2;
            int j = (this.height - 140) / 2;

            for(SkillTreeScreen SkillTreeScreen : this.skillTrees.values()) {
                if (SkillTreeScreen.getPage() == tabPage && SkillTreeScreen.isMouseOver(i, j, pMouseX, pMouseY)) {
                    this.perks.setSelectedTab(SkillTreeScreen.getPerk(), true);
                    break;
                }
            }
        }*/

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

//        atm it just closes the screen but could be used to switch skillTrees etc
//        opening the screen is handled in ClientEvents
        if (KeyBinding.keySkills.matches(pKeyCode, pScanCode)) {
            Minecraft.getInstance().setScreen((Screen) null);
            Minecraft.getInstance().mouseHandler.grabMouse();
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        // When left mouse in pressed the mouse is moved scroll the skill tree screen by the drag amount
        if (pButton != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } /*else if (this.selectedTab != null) {
                this.selectedTab.scroll(pDragX, pDragY);
            }*/

            return true;
        }
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int centerX = (this.width - 252) / 2;
        int centerY = (this.height - 140) / 2;

        // render from background to foreground
        this.renderBackground(pGuiGraphics);
        exButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        /*
        draws which page you've selected:
        "1/4" -> tabPage/maxPages

        if (maxPages != 0) {
            net.minecraft.network.chat.Component page = Component.literal(String.format("%d / %d", tabPage + 1, maxPages + 1));
            int width = this.font.width(page);
            pGuiGraphics.drawString(this.font, page.getVisualOrderText(), centerX + (252 / 2) - (width / 2), centerY - 44, -1);
        }*/
        this.renderInside(pGuiGraphics, pMouseX, pMouseY, centerX, centerY);
        this.renderWindow(pGuiGraphics, centerX, centerY);
        this.renderTooltips(pGuiGraphics, pMouseX, pMouseY, centerX, centerY);
    }

    private void renderInside(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
//        draw the skill tree

        SkillTreeScreen SkillTreeScreen = this.selectedSkillTree;
        if (SkillTreeScreen == null) {
            pGuiGraphics.fill(pOffsetX + 9, pOffsetY + 18, pOffsetX + 9 + 234, pOffsetY + 18 + 113, -16777216);
            int i = pOffsetX + 9 + 117;
            pGuiGraphics.drawCenteredString(this.font, NO_PERKS_LABEL, i, pOffsetY + 18 + 56 - 9 / 2, -1);
            pGuiGraphics.drawCenteredString(this.font, VERY_SAD_LABEL, i, pOffsetY + 18 + 113 - 9, -1);
        } else {
            SkillTreeScreen.drawContents(pGuiGraphics, pOffsetX + 9, pOffsetY + 18);
        }
    }

    public void renderWindow(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY) {

        RenderSystem.enableBlend();

//        window location is resource location
//        can be removed if it is a full screen

        pGuiGraphics.blit(WINDOW_RESOURCE_LOCATION, pOffsetX, pOffsetY, 0, 0, 252, 140);

        if (this.skillTrees.size() > 1) {

//            draws the actual tabs for each catagory: blank tabs

            for(SkillTreeScreen skillTreeScreen : this.skillTrees.values()) {
                if (true)//skillTreeScreen.getPage() == tabPage)
                    skillTreeScreen.drawSkillTreeTab(pGuiGraphics, pOffsetX, pOffsetY, skillTreeScreen == this.selectedSkillTree);
            }

//            draws the icons ontop of each tab

            for(SkillTreeScreen skillTreeScreen : this.skillTrees.values()) {
                if (true)//skillTreeScreen.getPage() == tabPage)
                    skillTreeScreen.drawIcon(pGuiGraphics, pOffsetX, pOffsetY);
            }
        }

        pGuiGraphics.drawString(this.font, getTitle(), pOffsetX + 8, pOffsetY + 6, 4210752, false);
    }

    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
        /*
        draw tool tips that are inside of the skill tree: perk button tooltips
        
        if (this.selectedTab != null) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float)(pOffsetX + 9), (float)(pOffsetY + 18), 400.0F);
            RenderSystem.enableDepthTest();
            this.selectedTab.drawTooltips(pGuiGraphics, pMouseX - pOffsetX - 9, pMouseY - pOffsetY - 18, pOffsetX, pOffsetY);
            RenderSystem.disableDepthTest();
            pGuiGraphics.pose().popPose();
        }
        
        draw tool tip for the tab if one is being hovered
        
        if (this.skillTrees.size() > 1) {
            for(SkillTreeScreen SkillTreeScreen : this.skillTrees.values()) {
                if (SkillTreeScreen.getPage() == tabPage && SkillTreeScreen.isMouseOver(pOffsetX, pOffsetY, (double)pMouseX, (double)pMouseY)) {
                    pGuiGraphics.renderTooltip(this.font, SkillTreeScreen.getTitle(), pMouseX, pMouseY);
                }
            }
        }*/

    }
    


    public void onAddPerkRoot(Perk pPerk) {
        SkillTreeScreen skillTreeScreen = SkillTreeScreen.create(this.minecraft, this, this.skillTrees.size(), pPerk);
        if (skillTreeScreen != null) {
            this.skillTrees.put(pPerk, skillTreeScreen);
        }
    }

    public void onRemovePerkRoot(Perk pPerk) {
    }

    /*public void onAddPerkTask(Perk perk) {
        SkillTreeScreen SkillTreeScreen = this.getSkillTree(perk);
        if (SkillTreeScreen != null) {
            SkillTreeScreen.addPerk(perk);
        }

    }

    public void onRemovePerkTask(Perk pPerk) {
    }*/

    /*public void onUpdatePerkProgress(Perk pPerk, PerkProgress progress) {
        PerkButton perkButton = this.getPerkButton(pPerk);
        if (perkButton != null) {
            perkButton.setProgress(progress);
        }

    }*/

    /*public void onSelectedTabChanged(@Nullable Perk perk) {
        this.selectedSkillTree = this.skillTrees.get(perk);
    }*/

    /*public void onPerksCleared() {
        this.skillTrees.clear();
        this.selectedSkillTree = null;
    }*/

    /*@Nullable
    public PerkButton getPerkButton(Perk perk) {
        SkillTreeScreen skillTree = this.getSkillTree(perk);
        return skillTree == null ? null : skillTree.getWidget(perk);
    }*/

    /*@Nullable
    private SkillTreeScreen getSkillTree(Perk perk) {
        while(perk.getParent() != null) {
            perk = perk.getParent();
        }

        return this.skillTrees.get(perk);
    }*/
    
}