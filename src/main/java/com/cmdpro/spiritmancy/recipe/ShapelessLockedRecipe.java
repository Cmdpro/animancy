package com.cmdpro.spiritmancy.recipe;


import com.cmdpro.spiritmancy.Spiritmancy;
import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.bookstate.BookUnlockStateManager;
import net.minecraft.advancements.Advancement;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.TransientCraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicReference;

public class ShapelessLockedRecipe extends ShapelessRecipe {
    private final ShapelessRecipe recipe;
    private final String entry;
    private final boolean mustRead;

    public ShapelessLockedRecipe(ShapelessRecipe recipe, String entry, boolean mustRead) {
        super(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getResultItem(RegistryAccess.EMPTY), recipe.getIngredients());
        this.recipe = recipe;
        this.entry = entry;
        this.mustRead = mustRead;
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        return super.matches(pInv, pLevel);
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        Player player = null;
        if (EffectiveSide.get().isServer()) {
            for (ServerPlayer i : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
                if (i.containerMenu == ((TransientCraftingContainer) pContainer).menu && pContainer.stillValid(i)) {
                    if (player != null) {
                        return ItemStack.EMPTY;
                    }
                    player = i;
                }
            }
        }
        if (EffectiveSide.get().isClient()) {
            player = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> () -> {
                return Minecraft.getInstance().player;
            });
        }
        if (playerHasNeededEntry(player)) {
            return recipe.assemble(pContainer, pRegistryAccess);
        } else {
            return ItemStack.EMPTY;
        }
    }

    public boolean playerHasNeededEntry(Player player) {
        ConcurrentMap<ResourceLocation, Set<ResourceLocation>> entries = BookUnlockStateManager.get().saveData.getUnlockStates(player.getUUID()).unlockedEntries;
        if (mustRead) {
            entries = BookUnlockStateManager.get().saveData.getUnlockStates(player.getUUID()).readEntries;
        }
        for (Map.Entry<ResourceLocation, Set<ResourceLocation>> i : entries.entrySet()) {
            for (ResourceLocation o : i.getValue()) {
                if (o.toString().equals(entry)) {
                    return true;
                }
            }
        }
        return false;
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

    public static class Serializer implements RecipeSerializer<ShapelessLockedRecipe> {
        public static final ShapelessLockedRecipe.Serializer INSTANCE = new ShapelessLockedRecipe.Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Spiritmancy.MOD_ID,"shapelesslockedrecipe");

        @Override
        public ShapelessLockedRecipe fromJson(ResourceLocation id, JsonObject json) {
            ShapelessRecipe recipe = RecipeSerializer.SHAPELESS_RECIPE.fromJson(id, json);
            String entry = json.get("entry").getAsString();
            boolean mustRead = true;
            if (json.has("mustRead")) {
                mustRead = json.get("mustRead").getAsBoolean();
            }
            return new ShapelessLockedRecipe(recipe, entry, mustRead);
        }

        @Override
        public ShapelessLockedRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            String entry = buf.readUtf();
            boolean mustRead = buf.readBoolean();
            ShapelessRecipe recipe = RecipeSerializer.SHAPELESS_RECIPE.fromNetwork(id, buf);
            return new ShapelessLockedRecipe(recipe, entry, mustRead);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShapelessLockedRecipe recipe) {
            buf.writeUtf(recipe.entry);
            buf.writeBoolean(recipe.mustRead);
            RecipeSerializer.SHAPELESS_RECIPE.toNetwork(buf, recipe.recipe);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}