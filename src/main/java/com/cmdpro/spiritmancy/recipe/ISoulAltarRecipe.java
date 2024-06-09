package com.cmdpro.spiritmancy.recipe;

import com.cmdpro.spiritmancy.init.BlockInit;
import com.cmdpro.spiritmancy.init.RecipeInit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeType;

public interface ISoulAltarRecipe extends CraftingRecipe, IHasRequiredAdvancement, IHasSoulCost {
    @Override
    default RecipeType<?> getType() {
        return RecipeInit.SOULALTAR.get();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }

    @Override
    default ItemStack getToastSymbol() {
        return new ItemStack(BlockInit.SOULALTAR.get());
    }

    @Override
    default CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }
}
