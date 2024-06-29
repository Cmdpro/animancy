package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.recipe.ISoulAltarRecipe;
import com.cmdpro.animancy.recipe.ShapedSoulAltarRecipe;
import com.cmdpro.animancy.recipe.ShapelessSoulAltarRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Animancy.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Animancy.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ShapelessSoulAltarRecipe>> SHAPELESSSOULALTARRECIPE = RECIPES.register("shapelesssoulaltarrecipe", () -> ShapelessSoulAltarRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ShapedSoulAltarRecipe>> SHAPEDSOULALTARRECIPE = RECIPES.register("shapedsoulaltarrecipe", () -> ShapedSoulAltarRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<ISoulAltarRecipe>> SOULALTAR =
            RECIPE_TYPES.register("soulaltarrecipe", () -> RecipeType.simple(new ResourceLocation(Animancy.MOD_ID, "soulaltarrecipe")));
    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
