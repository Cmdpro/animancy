package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.recipe.ShapedLockedRecipe;
import com.cmdpro.spiritmancy.recipe.ShapelessLockedRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Spiritmancy.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ShapedLockedRecipe>> LOCKED_SHAPED_SERIALIZER =
            RECIPES.register("shaped_locked_recipe", () -> ShapedLockedRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ShapelessLockedRecipe>> LOCKED_SHAPELESS_SERIALIZER =
            RECIPES.register("shapeless_locked_recipe", () -> ShapelessLockedRecipe.Serializer.INSTANCE);
    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
    }
}
