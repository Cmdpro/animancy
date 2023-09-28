package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.recipe.ShapedLockedRecipe;
import com.cmdpro.spiritmancy.recipe.ShapelessLockedRecipe;
import com.cmdpro.spiritmancy.recipe.SoulShaperRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeInit {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Spiritmancy.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ShapedLockedRecipe>> LOCKED_SHAPED_SERIALIZER =
            RECIPES.register("shapedlockedrecipe", () -> ShapedLockedRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<ShapelessLockedRecipe>> LOCKED_SHAPELESS_SERIALIZER =
            RECIPES.register("shapelesslockedrecipe", () -> ShapelessLockedRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<SoulShaperRecipe>> SOUL_SHAPER =
            RECIPES.register("soulshaper", () -> SoulShaperRecipe.Serializer.INSTANCE);
    public static void register(IEventBus eventBus) {
        RECIPES.register(eventBus);
    }
}
