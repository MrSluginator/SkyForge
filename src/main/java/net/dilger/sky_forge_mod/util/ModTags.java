package net.dilger.sky_forge_mod.util;

import net.dilger.sky_forge_mod.SkyForgeMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");
        public static final TagKey<Block> NEEDS_SAPPHIRE_TOOL = tag("needs_sapphire_tool");
        public static final TagKey<Block> MINING_SKILL_OBJECTIVES = tag("mining_skill_objectives");
        public static final TagKey<Block> FARMING_SKILL_OBJECTIVES = tag("farming_skill_objectives");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(SkyForgeMod.MOD_ID, name));
        }
    }

    public static class Items {

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(SkyForgeMod.MOD_ID, name));
        }
    }
}
