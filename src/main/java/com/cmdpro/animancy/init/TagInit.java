package com.cmdpro.animancy.init;

import com.cmdpro.animancy.Animancy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class TagInit {
    public static class Blocks {
        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(Animancy.MOD_ID, name));
        }
    }
    public static class Items {
        public static final TagKey<Item> SOULDAGGERS = tag("souldaggers");
        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(Animancy.MOD_ID, name));
        }
    }
}
