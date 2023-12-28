package net.dilger.sky_forge_mod.skill;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.List;

public class SkillTreeNodePosition {
    private final Perk perk;
    @Nullable
    private final SkillTreeNodePosition parent;
    @Nullable
    private final SkillTreeNodePosition previousSibling;
    private final int childIndex;
    private final List<SkillTreeNodePosition> children = Lists.newArrayList();
    private SkillTreeNodePosition ancestor;
    @Nullable
    private SkillTreeNodePosition thread;
    private int x;
    private int y;
    private float mod;
    private float change;
    private float shift;

    public SkillTreeNodePosition(Perk perk, @Nullable SkillTreeNodePosition pParent, @Nullable SkillTreeNodePosition pPreviousSibling, int pChildIndex, int pX) {

        this.perk = perk;
        this.parent = pParent;
        this.previousSibling = pPreviousSibling;
        this.childIndex = pChildIndex;
        this.ancestor = this;
        this.x = 0;
        this.y = 0;
        SkillTreeNodePosition treenodeposition = null;

        for(Perk advancement : perk.getChildren()) {
            treenodeposition = this.addChild(advancement, treenodeposition);
        }


    }

    @Nullable
    private SkillTreeNodePosition addChild(Perk perk, @Nullable SkillTreeNodePosition pPrevious) {
        pPrevious = new SkillTreeNodePosition(perk, this, pPrevious, this.children.size() + 1, this.x + 1);
        this.children.add(pPrevious);
        
        return pPrevious;
    }

    private void firstWalk() {
        if (this.children.isEmpty()) {
            if (this.previousSibling != null) {
                this.y = this.previousSibling.y + 1;
            } else {
                this.y = 0;
            }

        } else {
            SkillTreeNodePosition treenodeposition = null;

            for(SkillTreeNodePosition treenodeposition1 : this.children) {
                treenodeposition1.firstWalk();
                treenodeposition = treenodeposition1.apportion(treenodeposition == null ? treenodeposition1 : treenodeposition);
            }

            this.executeShifts();
            int f = ((this.children.get(0)).y + (this.children.get(this.children.size() - 1)).y) / 2;
            if (this.previousSibling != null) {
                this.y = this.previousSibling.y + 1;
                this.mod = this.y - f;
            } else {
                this.y = f;
            }

        }
    }

    private float secondWalk(float pOffsetY, int pColumnX, float pSubtreeTopY) {
        this.y += pOffsetY;
        this.x = pColumnX;
        if (this.y < pSubtreeTopY) {
            pSubtreeTopY = this.y;
        }

        for(SkillTreeNodePosition treenodeposition : this.children) {
            pSubtreeTopY = treenodeposition.secondWalk(pOffsetY + this.mod, pColumnX + 1, pSubtreeTopY);
        }

        return pSubtreeTopY;
    }

    private void thirdWalk(float pY) {
        this.y += pY;

        for(SkillTreeNodePosition treenodeposition : this.children) {
            treenodeposition.thirdWalk(pY);
        }

    }

    private void executeShifts() {
        float f = 0.0F;
        float f1 = 0.0F;

        for(int i = this.children.size() - 1; i >= 0; --i) {
            SkillTreeNodePosition treenodeposition = this.children.get(i);
            treenodeposition.y += f;
            treenodeposition.mod += f;
            f1 += treenodeposition.change;
            f += treenodeposition.shift + f1;
        }

    }

    @Nullable
    private SkillTreeNodePosition previousOrThread() {
        if (this.thread != null) {
            return this.thread;
        } else {
            return !this.children.isEmpty() ? this.children.get(0) : null;
        }
    }

    @Nullable
    private SkillTreeNodePosition nextOrThread() {
        if (this.thread != null) {
            return this.thread;
        } else {
            return !this.children.isEmpty() ? this.children.get(this.children.size() - 1) : null;
        }
    }

    private SkillTreeNodePosition apportion(SkillTreeNodePosition pNode) {
        if (this.previousSibling == null) {
            return pNode;
        } else {
            SkillTreeNodePosition treenodeposition = this;
            SkillTreeNodePosition treenodeposition1 = this;
            SkillTreeNodePosition treenodeposition2 = this.previousSibling;
            SkillTreeNodePosition treenodeposition3 = this.parent.children.get(0);
            float f = this.mod;
            float f1 = this.mod;
            float f2 = treenodeposition2.mod;

            float f3;
            for(f3 = treenodeposition3.mod; treenodeposition2.nextOrThread() != null && treenodeposition.previousOrThread() != null; f1 += treenodeposition1.mod) {
                treenodeposition2 = treenodeposition2.nextOrThread();
                treenodeposition = treenodeposition.previousOrThread();
                treenodeposition3 = treenodeposition3.previousOrThread();
                treenodeposition1 = treenodeposition1.nextOrThread();
                treenodeposition1.ancestor = this;
                float f4 = treenodeposition2.y + f2 - (treenodeposition.y + f) + 1.0F;
                if (f4 > 0.0F) {
                    treenodeposition2.getAncestor(this, pNode).moveSubtree(this, f4);
                    f += f4;
                    f1 += f4;
                }

                f2 += treenodeposition2.mod;
                f += treenodeposition.mod;
                f3 += treenodeposition3.mod;
            }

            if (treenodeposition2.nextOrThread() != null && treenodeposition1.nextOrThread() == null) {
                treenodeposition1.thread = treenodeposition2.nextOrThread();
                treenodeposition1.mod += f2 - f1;
            } else {
                if (treenodeposition.previousOrThread() != null && treenodeposition3.previousOrThread() == null) {
                    treenodeposition3.thread = treenodeposition.previousOrThread();
                    treenodeposition3.mod += f - f3;
                }

                pNode = this;
            }

            return pNode;
        }
    }

    private void moveSubtree(SkillTreeNodePosition pNode, float pShift) {
        float f = (float)(pNode.childIndex - this.childIndex);
        if (f != 0.0F) {
            pNode.change -= pShift / f;
            this.change += pShift / f;
        }

        pNode.shift += pShift;
        pNode.y += pShift;
        pNode.mod += pShift;
    }

    private SkillTreeNodePosition getAncestor(SkillTreeNodePosition pSelf, SkillTreeNodePosition pOther) {
        return this.ancestor != null && pSelf.parent.children.contains(this.ancestor) ? this.ancestor : pOther;
    }

    private void finalizePosition() {
        this.perk.getDisplay().setTreeLocation(this.x, (int) this.y);
        

        if (!this.children.isEmpty()) {
            for(SkillTreeNodePosition treenodeposition : this.children) {
                treenodeposition.finalizePosition();
            }
        }

    }

    public static void run(Perk pRoot) {
        SkillTreeNodePosition treenodeposition = new SkillTreeNodePosition(pRoot, (SkillTreeNodePosition) null, (SkillTreeNodePosition) null, 1, 0);
        treenodeposition.firstWalk();
        float f = treenodeposition.secondWalk(0.0F, 0, treenodeposition.y);
        if (f < 0.0F) {
            treenodeposition.thirdWalk(-f);
        }

        treenodeposition.finalizePosition();

    }

    public static SkillTreeNodePosition runFirstWalk(Perk root) {
        SkillTreeNodePosition treenodeposition = new SkillTreeNodePosition(root, (SkillTreeNodePosition) null, (SkillTreeNodePosition) null, 1, 0);
        treenodeposition.firstWalk();
        treenodeposition.finalizePosition();
        return treenodeposition;
    }
    static float f;
    public static void runSecondWalk(SkillTreeNodePosition treenodeposition) {
        if (treenodeposition == null) {
            Minecraft.getInstance().player.sendSystemMessage(Component.literal("must do first walk"));
            return;
        }
        f = treenodeposition.secondWalk(0.0F, 0, treenodeposition.y);
        treenodeposition.finalizePosition();
    }

    public static void runThirdWalk(SkillTreeNodePosition treenodeposition) {
        treenodeposition.thirdWalk(-f);
        treenodeposition.finalizePosition();
    }
}