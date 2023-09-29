package com.cmdpro.spiritmancy.item;

import com.cmdpro.spiritmancy.init.ItemInit;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.crafting.StrictNBTIngredient;

public class ModTiers {
    public static final ForgeTier SCYTHE = new ForgeTier(4, 1800, 8f,
            4f, 15, BlockTags.NEEDS_DIAMOND_TOOL,
            () -> Ingredient.of(Items.IRON_INGOT));
}
