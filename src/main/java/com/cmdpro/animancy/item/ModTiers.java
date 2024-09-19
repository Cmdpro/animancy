package com.cmdpro.animancy.item;

import com.cmdpro.animancy.registry.ItemRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;

public class ModTiers {
    public static final Tier SOULMETAL = new SimpleTier(BlockTags.INCORRECT_FOR_IRON_TOOL, 1800, 6.0f, 4f, 15,
            () -> Ingredient.of(ItemRegistry.SOULMETAL.get()));
    public static final Tier ANIMAGITE = new SimpleTier(BlockTags.INCORRECT_FOR_DIAMOND_TOOL, 2400, 8.0f, 6f, 15,
            () -> Ingredient.of(ItemRegistry.ANIMAGITE_INGOT.get()));
}
