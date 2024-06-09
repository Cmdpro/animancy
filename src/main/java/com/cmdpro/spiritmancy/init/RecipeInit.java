package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.recipe.ISoulAltarRecipe;
import com.cmdpro.spiritmancy.recipe.ShapedSoulAltarRecipe;
import com.cmdpro.spiritmancy.recipe.ShapelessSoulAltarRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Spiritmancy.MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Spiritmancy.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ShapelessSoulAltarRecipe>> SHAPELESSSOULALTARRECIPE = RECIPES.register("shapelesssoulaltarrecipe", () -> ShapelessSoulAltarRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ShapedSoulAltarRecipe>> SHAPEDSOULALTARRECIPE = RECIPES.register("shapedsoulaltarrecipe", () -> ShapedSoulAltarRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeType<ISoulAltarRecipe>> SOULALTAR =
            RECIPE_TYPES.register("soulaltarrecipe", () -> RecipeType.simple(new ResourceLocation(Spiritmancy.MOD_ID, "soulaltarrecipe")));
    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
