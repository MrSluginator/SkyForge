package net.dilger.sky_forge_mod.gui.screen.skill_tree;

import com.google.common.collect.Maps;
import net.dilger.sky_forge_mod.database.data.perks.Perk;
import net.dilger.sky_forge_mod.gui.screen.skill_tree.buttons.PerkButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.Map;

/*
The skill tree screen encapsulates the entire screen that gets rendered
It's essential the parent screen to all other screen in the skill tree menu

Skills are of several types each with their own tab and screen
*/

public class SkillScreen extends Screen {

    private final Map<Perk, SkillTreeScreen> skillTrees = Maps.newLinkedHashMap();
    private SkillTreeScreen selectedSkillTree;
    private boolean isScrolling;

    protected SkillScreen() {
//    constructor
        super(Component.literal("Skill Tree Screen"));
        // should implement cient skills: the skills that are saved onto the player
    }

    protected void init() {
        /*
        initialized the selected skill tree as well as the tabs to pick other ones
        modifies the page if there are too many tabs at the top by adding arrow buttons

        this.tabs.clear();
        this.selectedTab = null;
        this.perks.setListener(this);
        if (this.selectedTab == null && !this.tabs.isEmpty()) {
            this.perks.setSelectedTab(this.tabs.values().iterator().next().getPerk(), true);
        } else {
            this.perks.setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getPerk(), true);
        }
        if (this.tabs.size() > SkillTreeScreenType.MAX_TABS) {
            int guiLeft = (this.width - 252) / 2;
            int guiTop = (this.height - 140) / 2;
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal("<"), b -> tabPage = java.lang.Math.max(tabPage - 1, 0       ))
                    .pos(guiLeft, guiTop - 50).size(20, 20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal(">"), b -> tabPage = Math.min(tabPage + 1, maxPages))
                    .pos(guiLeft + WINDOW_WIDTH - 20, guiTop - 50).size(20, 20).build());
            maxPages = this.tabs.size() / SkillTreeScreenType.MAX_TABS;
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
        check if any of the tabs have been clicked
        change the selected tab if there has

        if (pButton == 0) {
            int i = (this.width - 252) / 2;
            int j = (this.height - 140) / 2;

            for(SkillTreeScreen SkillTreeScreen : this.tabs.values()) {
                if (SkillTreeScreen.getPage() == tabPage && SkillTreeScreen.isMouseOver(i, j, pMouseX, pMouseY)) {
                    this.perks.setSelectedTab(SkillTreeScreen.getPerk(), true);
                    break;
                }
            }
        }*/

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        /*
        check to see if the keyCode received matches any in the key file
        atm it just closes or open the screen but could be used to switch tabs etc
        if (this.minecraft.options.keyPerks.matches(pKeyCode, pScanCode)) {
            this.minecraft.setScreen((Screen)null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }*/
        return false;
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
        /*
        draw the skill tree

        SkillTreeScreen SkillTreeScreen = this.selectedTab;
        if (SkillTreeScreen == null) {
            pGuiGraphics.fill(pOffsetX + 9, pOffsetY + 18, pOffsetX + 9 + 234, pOffsetY + 18 + 113, -16777216);
            int i = pOffsetX + 9 + 117;
            pGuiGraphics.drawCenteredString(this.font, NO_PERKS_LABEL, i, pOffsetY + 18 + 56 - 9 / 2, -1);
            pGuiGraphics.drawCenteredString(this.font, VERY_SAD_LABEL, i, pOffsetY + 18 + 113 - 9, -1);
        } else {
            SkillTreeScreen.drawContents(pGuiGraphics, pOffsetX + 9, pOffsetY + 18);
        }*/
    }

    public void renderWindow(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY) {
        /*
        RenderSystem.enableBlend();

        window location assumes that the window can be moved
        can be removed if it is a full screen

        pGuiGraphics.blit(WINDOW_LOCATION, pOffsetX, pOffsetY, 0, 0, 252, 140);

        if (this.tabs.size() > 1) {

            draws the actual tabs for each catagory: blank tabs

            for(SkillTreeScreen SkillTreeScreen : this.tabs.values()) {
                if (SkillTreeScreen.getPage() == tabPage)
                    SkillTreeScreen.drawTab(pGuiGraphics, pOffsetX, pOffsetY, SkillTreeScreen == this.selectedTab);
            }

            draws the icons ontop of each tab

            for(SkillTreeScreen SkillTreeScreen1 : this.tabs.values()) {
                if (SkillTreeScreen1.getPage() == tabPage)
                    SkillTreeScreen1.drawIcon(pGuiGraphics, pOffsetX, pOffsetY);
            }
        }

        pGuiGraphics.drawString(this.font, TITLE, pOffsetX + 8, pOffsetY + 6, 4210752, false);*/
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
        
        if (this.tabs.size() > 1) {
            for(SkillTreeScreen SkillTreeScreen : this.tabs.values()) {
                if (SkillTreeScreen.getPage() == tabPage && SkillTreeScreen.isMouseOver(pOffsetX, pOffsetY, (double)pMouseX, (double)pMouseY)) {
                    pGuiGraphics.renderTooltip(this.font, SkillTreeScreen.getTitle(), pMouseX, pMouseY);
                }
            }
        }*/

    }
    
    /*
    not nessecary for now

    public void onAddPerkRoot(Perk pPerk) {
        SkillTreeScreen skillTreeScreen = SkillTreeScreen.create(this.minecraft, this, this.skillTrees.size(), pPerk);
        if (skillTreeScreen != null) {
            this.skillTrees.put(pPerk, skillTreeScreen);
        }
    }

    public void onRemovePerkRoot(Perk pPerk) {
    }

    public void onAddPerkTask(Perk perk) {
        SkillTreeScreen SkillTreeScreen = this.getSkillTree(perk);
        if (SkillTreeScreen != null) {
            SkillTreeScreen.addPerk(perk);
        }

    }

    public void onRemovePerkTask(Perk pPerk) {
    }

    public void onUpdatePerkProgress(Perk pPerk, PerkProgress progress) {
        PerkButton perkButton = this.getPerkButton(pPerk);
        if (perkButton != null) {
            perkButton.setProgress(progress);
        }

    }

    public void onSelectedTabChanged(@Nullable Perk perk) {
        this.selectedSkillTree = this.skillTrees.get(perk);
    }

    public void onPerksCleared() {
        this.skillTrees.clear();
        this.selectedSkillTree = null;
    }

    @Nullable
    public PerkButton getPerkButton(Perk perk) {
        SkillTreeScreen skillTree = this.getSkillTree(perk);
        return skillTree == null ? null : skillTree.getWidget(perk);
    }

    @Nullable
    private SkillTreeScreen getSkillTree(Perk perk) {
        while(perk.getParent() != null) {
            perk = perk.getParent();
        }

        return this.skillTrees.get(perk);
    }*/
    
}