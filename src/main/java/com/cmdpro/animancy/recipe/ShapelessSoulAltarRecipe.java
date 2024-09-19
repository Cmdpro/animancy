package com.cmdpro.animancy.recipe;


import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.RecipeRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShapelessSoulAltarRecipe implements ISoulAltarRecipe {
    private final ResourceLocation advancement;
    private final Map<ResourceLocation, Float> souls;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;
    final List<ResourceLocation> upgrades;
    final int maxCraftingTime;

    public ShapelessSoulAltarRecipe(ItemStack result, NonNullList<Ingredient> ingredients, ResourceLocation advancement, Map<ResourceLocation, Float> souls, List<ResourceLocation> upgrades, int maxCraftingTime) {
        this.advancement = advancement;
        this.souls = souls;
        this.result = result;
        this.ingredients = ingredients;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
        this.upgrades = upgrades;
        this.maxCraftingTime = maxCraftingTime;
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistryAccess) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }


    @Override
    public boolean matches(CraftingInput pInv, Level pLevel) {
        StackedContents stackedcontents = new StackedContents();
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int i = 0;

        for(int j = 0; j < pInv.size(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (isSimple ? stackedcontents.canCraft(this, null) : net.neoforged.neoforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }

    @Override
    public ItemStack assemble(CraftingInput p_345149_, HolderLookup.Provider p_346030_) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= this.ingredients.size();
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

    public static class Serializer implements RecipeSerializer<ShapelessSoulAltarRecipe> {
        public static final MapCodec<ShapelessSoulAltarRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                ItemStack.CODEC.fieldOf("result").forGetter(p_301142_ -> p_301142_.result),
                Ingredient.CODEC_NONEMPTY
                        .listOf()
                        .fieldOf("ingredients")
                        .flatXmap(
                                p_301021_ -> {
                                    Ingredient[] aingredient = p_301021_
                                            .toArray(Ingredient[]::new); //Forge skip the empty check and immediatly create the array.
                                    if (aingredient.length == 0) {
                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                    } else {
                                        return aingredient.length > ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()
                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe. The maximum is: %s".formatted(ShapedRecipePattern.getMaxHeight() * ShapedRecipePattern.getMaxWidth()))
                                                : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                    }
                                },
                                DataResult::success
                        )
                        .forGetter(p_300975_ -> p_300975_.ingredients),
                ResourceLocation.CODEC.fieldOf("advancement").forGetter((r) -> r.advancement),
                Codec.INT.fieldOf("craftingTime").forGetter((r) -> r.maxCraftingTime),
                ResourceLocation.CODEC.listOf().fieldOf("upgrades").forGetter((r) -> r.upgrades),
                Codec.unboundedMap(ResourceLocation.CODEC, Codec.FLOAT).fieldOf("souls").forGetter(r -> r.souls)
        ).apply(instance, (result, input, advancement, craftingTime, upgrades, souls) -> new ShapelessSoulAltarRecipe(result, input, advancement, souls, upgrades, craftingTime)));

        public static final StreamCodec<RegistryFriendlyByteBuf, ShapelessSoulAltarRecipe> STREAM_CODEC = StreamCodec.of(
                (buf, obj) -> {
                    buf.writeVarInt(obj.ingredients.size());

                    for (Ingredient ingredient : obj.ingredients) {
                        Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
                    }

                    ItemStack.STREAM_CODEC.encode(buf, obj.result);
                    buf.writeResourceLocation(obj.advancement);
                    buf.writeInt(obj.maxCraftingTime);
                    buf.writeCollection(obj.upgrades, FriendlyByteBuf::writeResourceLocation);
                    buf.writeMap(obj.souls, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeFloat);
                },
                (buf) -> {
                    int i = buf.readVarInt();
                    NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
                    nonnulllist.replaceAll(p_319735_ -> Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
                    ItemStack itemstack = ItemStack.STREAM_CODEC.decode(buf);
                    ResourceLocation advancement = buf.readResourceLocation();
                    int craftingTime = buf.readInt();
                    List<ResourceLocation> upgrades = buf.readList(FriendlyByteBuf::readResourceLocation);
                    Map<ResourceLocation, Float> souls = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readFloat);
                    return new ShapelessSoulAltarRecipe(itemstack, nonnulllist, advancement, souls, upgrades, craftingTime);
                }
        );

        public static final ShapelessSoulAltarRecipe.Serializer INSTANCE = new ShapelessSoulAltarRecipe.Serializer();

        @Override
        public MapCodec<ShapelessSoulAltarRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ShapelessSoulAltarRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}