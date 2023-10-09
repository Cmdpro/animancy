package com.cmdpro.spiritmancy.integration;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.init.BlockInit;
import com.cmdpro.spiritmancy.init.ItemInit;
import com.cmdpro.spiritmancy.recipe.SoulAltarRecipe;
import com.cmdpro.spiritmancy.recipe.SoulShaperRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.subtypes.IIngredientSubtypeInterpreter;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.*;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.*;

@JeiPlugin
public class JEISpiritmancyPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Spiritmancy.MOD_ID, "jei_plugin");
    }

    public static IJeiRuntime runTime;
    public static final RecipeType soulAltarCategory = RecipeType.create(Spiritmancy.MOD_ID, SoulAltarRecipe.Type.ID, SoulAltarRecipe.class);
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new SoulAltarRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();
        List<SoulAltarRecipe> recipes = rm.getAllRecipesFor(SoulAltarRecipe.Type.INSTANCE);
        registration.addRecipes(soulAltarCategory, recipes);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.useNbtForSubtypes(ItemInit.FULLSOULCRYSTAL.get());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(BlockInit.SOULALTAR.get()), soulAltarCategory);
    }
    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

        runTime = jeiRuntime;

    }
}
