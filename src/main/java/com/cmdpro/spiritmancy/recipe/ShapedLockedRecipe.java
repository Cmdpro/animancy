package com.cmdpro.spiritmancy.recipe;


import com.cmdpro.spiritmancy.Spiritmancy;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;

public class ShapedLockedRecipe extends ShapedRecipe {
    private final ShapedRecipe recipe;
    private final String advancement;

    public ShapedLockedRecipe(ShapedRecipe recipe, String advancement) {
        super(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem(RegistryAccess.EMPTY));
        this.recipe = recipe;
        this.advancement = advancement;
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        return super.matches(pInv, pLevel);
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        if (EffectiveSide.get().isServer()) {
            ServerPlayer player = null;
            for(ServerPlayer i : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                if(i.containerMenu == ((TransientCraftingContainer)pContainer).menu && pContainer.stillValid(i)) {
                    if(player != null) {
                        return ItemStack.EMPTY;
                    }
                    player = i;
                }
            }
            if (playerHasNeededAdvancement(player)) {
                return recipe.assemble(pContainer, pRegistryAccess);
            } else {
                return ItemStack.EMPTY;
            }
        }
        return ItemStack.EMPTY;
    }

    public boolean playerHasNeededAdvancement(ServerPlayer player) {
        Collection<ResourceLocation> finishedAdvancments = new ArrayList<>();
        for (Advancement i : player.level().getServer().getAdvancements().getAllAdvancements()) {
            if (player.getAdvancements().getOrStartProgress(i).isDone()) {
                finishedAdvancments.add(i.getId());
            }
        }
        return finishedAdvancments.contains(ResourceLocation.tryParse(advancement));
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    public static class Serializer implements RecipeSerializer<ShapedLockedRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Spiritmancy.MOD_ID,"shaped_locked_recipe");

        @Override
        public ShapedLockedRecipe fromJson(ResourceLocation id, JsonObject json) {
            ShapedRecipe recipe = RecipeSerializer.SHAPED_RECIPE.fromJson(id, json);
            String advancement = json.get("advancement").getAsString();
            return new ShapedLockedRecipe(recipe, advancement);
        }

        @Override
        public ShapedLockedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String advancement = buf.readUtf();
            ShapedRecipe recipe = RecipeSerializer.SHAPED_RECIPE.fromNetwork(id, buf);
            return new ShapedLockedRecipe(recipe, advancement);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShapedLockedRecipe recipe) {
            buf.writeUtf(recipe.advancement);
            RecipeSerializer.SHAPED_RECIPE.toNetwork(buf, recipe.recipe);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}