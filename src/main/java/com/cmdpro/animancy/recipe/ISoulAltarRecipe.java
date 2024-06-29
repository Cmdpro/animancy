package com.cmdpro.animancy.recipe;

import com.cmdpro.animancy.registry.BlockRegistry;
import com.cmdpro.animancy.registry.RecipeRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface ISoulAltarRecipe extends CraftingRecipe, IHasRequiredAdvancement, IHasSoulCost {
    @Override
    default RecipeType<?> getType() {
        return RecipeRegistry.SOULALTAR.get();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }

    @Override
    default ItemStack getToastSymbol() {
        return new ItemStack(BlockRegistry.SOULALTAR.get());
    }

    @Override
    default CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }
}
