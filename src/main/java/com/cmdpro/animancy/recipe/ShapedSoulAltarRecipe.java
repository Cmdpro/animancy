package com.cmdpro.animancy.recipe;


import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.RecipeRegistry;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.crafting.CraftingHelper;

import java.util.*;

public class ShapedSoulAltarRecipe implements ISoulAltarRecipe {
    private final ResourceLocation advancement;
    private final Map<ResourceLocation, Float> souls;
    final ShapedRecipePattern pattern;
    final ItemStack result;
    final List<ResourceLocation> upgrades;
    final int maxCraftingTime;

    public ShapedSoulAltarRecipe(ShapedRecipePattern pPattern, ItemStack pResult, ResourceLocation advancement, Map<ResourceLocation, Float> souls, List<ResourceLocation> upgrades, int maxCraftingTime) {
        this.advancement = advancement;
        this.souls = souls;
        this.result = pResult;
        this.upgrades = upgrades;
        this.maxCraftingTime = maxCraftingTime;
        this.pattern = pPattern;
    }
    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.pattern.ingredients();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= this.pattern.width() && pHeight >= this.pattern.height();
    }

    @Override
    public boolean matches(CraftingInput pInv, Level pLevel) {
        return this.pattern.matches(pInv);
    }

    public ItemStack assemble(CraftingInput pContainer, HolderLookup.Provider pRegistryAccess) {
        return this.getResultItem(pRegistryAccess).copy();
    }

    public int getWidth() {
        return this.pattern.width();
    }

    public int getHeight() {
        return this.pattern.height();
    }

    @Override
    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream().filter(p_151277_ -> !p_151277_.isEmpty()).anyMatch(Ingredient::hasNoItems);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeRegistry.SOULALTAR.get();
    }

    @Override
    public List<ResourceLocation> getUpgrades() {
        return upgrades;
    }

    @Override
    public int getMaxCraftingTime() {
        return maxCraftingTime;
    }

    @Override
    public ResourceLocation getAdvancement() {
        return advancement;
    }

    @Override
    public Map<ResourceLocation, Float> getSoulCost() {
        return souls;
    }
    public static class Serializer implements RecipeSerializer<ShapedSoulAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<ShapedSoulAltarRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ShapedRecipePattern.MAP_CODEC.forGetter(p_311733_ -> p_311733_.pattern),
                ItemStack.CODEC.fieldOf("result").forGetter(p_311730_ -> p_311730_.result),
                ResourceLocation.CODEC.fieldOf("advancement").forGetter((r) -> r.advancement),
                Codec.INT.fieldOf("craftingTime").forGetter((r) -> r.maxCraftingTime),
                ResourceLocation.CODEC.listOf().fieldOf("upgrades").forGetter((r) -> r.upgrades),
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.FLOAT).fieldOf("souls").forGetter(r -> r.souls)
        ).apply(instance, (pattern, result, advancement, craftingTime, upgrades, souls) -> new ShapedSoulAltarRecipe(pattern, result, advancement, souls, upgrades, craftingTime)));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapedSoulAltarRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, obj) -> {
                    ShapedRecipePattern.STREAM_CODEC.encode(buf, obj.pattern);
                    ItemStack.STREAM_CODEC.encode(buf, obj.result);
                    buf.writeResourceLocation(obj.advancement);
                    buf.writeInt(obj.maxCraftingTime);
                    buf.writeCollection(obj.upgrades, FriendlyByteBuf::writeResourceLocation);
                    buf.writeMap(obj.souls, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeFloat);
                },
                (buf) -> {
                    ShapedRecipePattern shapedrecipepattern = ShapedRecipePattern.STREAM_CODEC.decode(buf);
                    ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buf);
                    ResourceLocation advancement = buf.readResourceLocation();
                    int craftingTime = buf.readInt();
                    List<ResourceLocation> upgrades = buf.readList(FriendlyByteBuf::readResourceLocation);
                    Map<ResourceLocation, Float> souls = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readFloat);
                    return new ShapedSoulAltarRecipe(shapedrecipepattern, itemstack, advancement, souls, upgrades, craftingTime);
                }
        );

        @Override
        public MapCodec<ShapedSoulAltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapedSoulAltarRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}