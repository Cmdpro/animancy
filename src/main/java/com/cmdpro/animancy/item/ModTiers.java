package com.cmdpro.animancy.item;

import com.cmdpro.animancy.registry.ItemRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class ModTiers {
    public static final ForgeTier SCYTHE = new ForgeTier(4, 1800, 8f,
            4f, 15, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ItemRegistry.SOULMETAL.get()));
    public static final ForgeTier PURGATORY = new ForgeTier(4, 2400, 8f,
            7f, 15, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(ItemRegistry.PURGATORYINGOT.get()));
}