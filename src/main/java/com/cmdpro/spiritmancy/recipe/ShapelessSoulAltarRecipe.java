package com.cmdpro.spiritmancy.recipe;


import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.init.RecipeInit;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShapelessSoulAltarRecipe implements ISoulAltarRecipe {
    private final ResourceLocation id;
    private final ResourceLocation advancement;
    private final Map<ResourceLocation, Float> souls;
    final ItemStack result;
    final NonNullList<Ingredient> ingredients;
    private final boolean isSimple;

    public ShapelessSoulAltarRecipe(ResourceLocation id, ItemStack result, NonNullList<Ingredient> ingredients, ResourceLocation advancement, Map<ResourceLocation, Float> souls) {
        this.id = id;
        this.advancement = advancement;
        this.souls = souls;
        this.result = result;
        this.ingredients = ingredients;
        this.isSimple = ingredients.stream().allMatch(Ingredient::isSimple);
    }


    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        StackedContents stackedcontents = new StackedContents();
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for(int j = 0; j < pInv.getContainerSize(); ++j) {
            ItemStack itemstack = pInv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                if (isSimple)
                    stackedcontents.accountStack(itemstack, 1);
                else inputs.add(itemstack);
            }
        }

        return i == this.ingredients.size() && (isSimple ? stackedcontents.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.ingredients) != null);
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.SOULALTAR.get();
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
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Spiritmancy.MOD_ID,"soulaltarrecipe");

        @Override
        public ShapelessSoulAltarRecipe fromJson(ResourceLocation id, JsonObject json) {
            ResourceLocation advancement = ResourceLocation.tryParse(GsonHelper.getAsString(json, "advancement", ""));
            HashMap<ResourceLocation, Float> souls = new HashMap<>();
            for (JsonElement i : GsonHelper.getAsJsonArray(json, "souls")) {
                souls.put(ResourceLocation.tryParse(GsonHelper.getAsString(i.getAsJsonObject(), "type")), GsonHelper.getAsFloat(i.getAsJsonObject(), "amount"));
            }
            NonNullList<Ingredient> nonnulllist = itemsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (nonnulllist.isEmpty()) {
                throw new JsonParseException("No ingredients for shapeless recipe");
            } else if (nonnulllist.size() > ShapedSoulAltarRecipe.MAX_WIDTH * ShapedSoulAltarRecipe.MAX_HEIGHT) {
                throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is " + (ShapedSoulAltarRecipe.MAX_WIDTH * ShapedSoulAltarRecipe.MAX_HEIGHT));
            } else {
                ItemStack itemstack = ShapedSoulAltarRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
                return new ShapelessSoulAltarRecipe(id, itemstack, nonnulllist, advancement, souls);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray pIngredientArray) {
            NonNullList<Ingredient> nonnulllist = NonNullList.create();

            for(int i = 0; i < pIngredientArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i), false);
                if (true || !ingredient.isEmpty()) { // FORGE: Skip checking if an ingredient is empty during shapeless recipe deserialization to prevent complex ingredients from caching tags too early. Can not be done using a config value due to sync issues.
                    nonnulllist.add(ingredient);
                }
            }

            return nonnulllist;
        }

        @Override
        public ShapelessSoulAltarRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int i = buf.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            Map<ResourceLocation, Float> map = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readFloat);
            boolean hasAdvancement = buf.readBoolean();
            ResourceLocation advancement = null;
            if (hasAdvancement) {
                advancement = buf.readResourceLocation();
            }
            return new ShapelessSoulAltarRecipe(id, itemstack, nonnulllist, advancement, map);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShapelessSoulAltarRecipe recipe) {
            buf.writeVarInt(recipe.ingredients.size());

            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.result);
            buf.writeMap(recipe.souls, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeFloat);
            buf.writeBoolean(recipe.advancement != null);
            if (recipe.advancement != null) {
                buf.writeResourceLocation(recipe.advancement);
            }
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}