package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.recipe.ISoulAltarRecipe;
import com.cmdpro.animancy.recipe.ShapedSoulAltarRecipe;
import com.cmdpro.animancy.recipe.ShapelessSoulAltarRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Animancy.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Animancy.MOD_ID);

    public static final Supplier<RecipeSerializer<ShapelessSoulAltarRecipe>> SHAPELESSSOULALTARRECIPE = register("shapeless_soul_altar_recipe", () -> ShapelessSoulAltarRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeSerializer<ShapedSoulAltarRecipe>> SHAPEDSOULALTARRECIPE = register("shaped_soul_altar_recipe", () -> ShapedSoulAltarRecipe.Serializer.INSTANCE);
    public static final Supplier<RecipeType<ISoulAltarRecipe>> SOULALTAR =
            registerType("soul_altar_recipe", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_altar_recipe")));

    private static <T extends RecipeType<?>> Supplier<T> registerType(final String name, final Supplier<T> item) {
        return RECIPE_TYPES.register(name, item);
    }
    private static <T extends RecipeSerializer<?>> Supplier<T> register(final String name, final Supplier<T> item) {
        return RECIPES.register(name, item);
    }
}
