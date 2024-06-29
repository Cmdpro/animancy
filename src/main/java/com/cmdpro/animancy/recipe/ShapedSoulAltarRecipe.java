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
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ShapedSoulAltarRecipe implements ISoulAltarRecipe {
    private final ResourceLocation advancement;
    private final Map<ResourceLocation, Float> souls;
    static int MAX_WIDTH = 3;
    static int MAX_HEIGHT = 3;
    public static void setCraftingSize(int width, int height) {
        if (MAX_WIDTH < width) MAX_WIDTH = width;
        if (MAX_HEIGHT < height) MAX_HEIGHT = height;
    }

    final int width;
    final int height;
    final NonNullList<Ingredient> recipeItems;
    final ItemStack result;
    private final ResourceLocation id;
    final boolean showNotification;

    public ShapedSoulAltarRecipe(ResourceLocation pId, int pWidth, int pHeight, NonNullList<Ingredient> pRecipeItems, ItemStack pResult, boolean pShowNotification, ResourceLocation advancement, Map<ResourceLocation, Float> souls) {
        this.advancement = advancement;
        this.souls = souls;
        this.width = pWidth;
        this.height = pHeight;
        this.recipeItems = pRecipeItems;
        this.result = pResult;
        this.id = pId;
        this.showNotification = pShowNotification;
    }

    @Override
    public boolean matches(CraftingContainer pInv, Level pLevel) {
        for(int i = 0; i <= pInv.getWidth() - this.width; ++i) {
            for(int j = 0; j <= pInv.getHeight() - this.height; ++j) {
                if (this.matches(pInv, i, j, true)) {
                    return true;
                }

                if (this.matches(pInv, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }
    private boolean matches(CraftingContainer pCraftingInventory, int pWidth, int pHeight, boolean pMirrored) {
        for(int i = 0; i < pCraftingInventory.getWidth(); ++i) {
            for(int j = 0; j < pCraftingInventory.getHeight(); ++j) {
                int k = i - pWidth;
                int l = j - pHeight;
                Ingredient ingredient = Ingredient.EMPTY;
                if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
                    if (pMirrored) {
                        ingredient = this.recipeItems.get(this.width - k - 1 + l * this.width);
                    } else {
                        ingredient = this.recipeItems.get(k + l * this.width);
                    }
                }

                if (!ingredient.test(pCraftingInventory.getItem(i + j * pCraftingInventory.getWidth()))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(CraftingContainer pContainer, RegistryAccess pRegistryAccess) {
        return this.getResultItem(pRegistryAccess).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= this.width && pHeight >= this.height;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.recipeItems;
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
    public ResourceLocation getAdvancement() {
        return advancement;
    }

    @Override
    public Map<ResourceLocation, Float> getSoulCost() {
        return souls;
    }
    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    static NonNullList<Ingredient> dissolvePattern(String[] pPattern, Map<String, Ingredient> pKeys, int pPatternWidth, int pPatternHeight) {
        NonNullList<Ingredient> nonnulllist = NonNullList.withSize(pPatternWidth * pPatternHeight, Ingredient.EMPTY);
        Set<String> set = Sets.newHashSet(pKeys.keySet());
        set.remove(" ");

        for(int i = 0; i < pPattern.length; ++i) {
            for(int j = 0; j < pPattern[i].length(); ++j) {
                String s = pPattern[i].substring(j, j + 1);
                Ingredient ingredient = pKeys.get(s);
                if (ingredient == null) {
                    throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
                }

                set.remove(s);
                nonnulllist.set(j + pPatternWidth * i, ingredient);
            }
        }

        if (!set.isEmpty()) {
            throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
        } else {
            return nonnulllist;
        }
    }

    @VisibleForTesting
    static String[] shrink(String... pToShrink) {
        int i = Integer.MAX_VALUE;
        int j = 0;
        int k = 0;
        int l = 0;

        for(int i1 = 0; i1 < pToShrink.length; ++i1) {
            String s = pToShrink[i1];
            i = Math.min(i, firstNonSpace(s));
            int j1 = lastNonSpace(s);
            j = Math.max(j, j1);
            if (j1 < 0) {
                if (k == i1) {
                    ++k;
                }

                ++l;
            } else {
                l = 0;
            }
        }

        if (pToShrink.length == l) {
            return new String[0];
        } else {
            String[] astring = new String[pToShrink.length - l - k];

            for(int k1 = 0; k1 < astring.length; ++k1) {
                astring[k1] = pToShrink[k1 + k].substring(i, j + 1);
            }

            return astring;
        }
    }

    public boolean isIncomplete() {
        NonNullList<Ingredient> nonnulllist = this.getIngredients();
        return nonnulllist.isEmpty() || nonnulllist.stream().filter((p_151277_) -> {
            return !p_151277_.isEmpty();
        }).anyMatch((p_151273_) -> {
            return net.minecraftforge.common.ForgeHooks.hasNoElements(p_151273_);
        });
    }

    private static int firstNonSpace(String pEntry) {
        int i;
        for(i = 0; i < pEntry.length() && pEntry.charAt(i) == ' '; ++i) {
        }

        return i;
    }

    private static int lastNonSpace(String pEntry) {
        int i;
        for(i = pEntry.length() - 1; i >= 0 && pEntry.charAt(i) == ' '; --i) {
        }

        return i;
    }

    static String[] patternFromJson(JsonArray pPatternArray) {
        String[] astring = new String[pPatternArray.size()];
        if (astring.length > MAX_HEIGHT) {
            throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
        } else if (astring.length == 0) {
            throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
        } else {
            for(int i = 0; i < astring.length; ++i) {
                String s = GsonHelper.convertToString(pPatternArray.get(i), "pattern[" + i + "]");
                if (s.length() > MAX_WIDTH) {
                    throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
                }

                if (i > 0 && astring[0].length() != s.length()) {
                    throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
                }

                astring[i] = s;
            }

            return astring;
        }
    }

    /**
     * Returns a key json object as a Java HashMap.
     */
    static Map<String, Ingredient> keyFromJson(JsonObject pKeyEntry) {
        Map<String, Ingredient> map = Maps.newHashMap();

        for(Map.Entry<String, JsonElement> entry : pKeyEntry.entrySet()) {
            if (entry.getKey().length() != 1) {
                throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
            }

            if (" ".equals(entry.getKey())) {
                throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
            }

            map.put(entry.getKey(), Ingredient.fromJson(entry.getValue(), false));
        }

        map.put(" ", Ingredient.EMPTY);
        return map;
    }

    public static ItemStack itemStackFromJson(JsonObject pStackObject) {
        return CraftingHelper.getItemStack(pStackObject, true, true);
    }

    public static Item itemFromJson(JsonObject pItemObject) {
        String s = GsonHelper.getAsString(pItemObject, "item");
        Item item = BuiltInRegistries.ITEM.getOptional(ResourceLocation.tryParse(s)).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown item '" + s + "'");
        });
        if (item == Items.AIR) {
            throw new JsonSyntaxException("Empty ingredient not allowed here");
        } else {
            return item;
        }
    }

    public static class Serializer implements RecipeSerializer<ShapedSoulAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Animancy.MOD_ID,"shapedsoulaltarrecipe");

        @Override
        public ShapedSoulAltarRecipe fromJson(ResourceLocation id, JsonObject json) {
            Map<String, Ingredient> map = ShapedSoulAltarRecipe.keyFromJson(GsonHelper.getAsJsonObject(json, "key"));
            String[] astring = ShapedSoulAltarRecipe.shrink(ShapedSoulAltarRecipe.patternFromJson(GsonHelper.getAsJsonArray(json, "pattern")));
            int i = astring[0].length();
            int j = astring.length;
            NonNullList<Ingredient> nonnulllist = ShapedSoulAltarRecipe.dissolvePattern(astring, map, i, j);
            ItemStack itemstack = ShapedSoulAltarRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
            boolean flag = GsonHelper.getAsBoolean(json, "show_notification", true);
            ResourceLocation advancement = ResourceLocation.tryParse(GsonHelper.getAsString(json, "advancement", ""));
            HashMap<ResourceLocation, Float> souls = new HashMap<>();
            for (JsonElement o : GsonHelper.getAsJsonArray(json, "souls")) {
                souls.put(ResourceLocation.tryParse(GsonHelper.getAsString(o.getAsJsonObject(), "type")), GsonHelper.getAsFloat(o.getAsJsonObject(), "amount"));
            }
            return new ShapedSoulAltarRecipe(id, i, j, nonnulllist, itemstack, flag, advancement, souls);
        }

        @Override
        public ShapedSoulAltarRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int i = buf.readVarInt();
            int j = buf.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i * j, Ingredient.EMPTY);

            for(int k = 0; k < nonnulllist.size(); ++k) {
                nonnulllist.set(k, Ingredient.fromNetwork(buf));
            }

            ItemStack itemstack = buf.readItem();
            boolean flag = buf.readBoolean();
            Map<ResourceLocation, Float> map = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readFloat);
            boolean hasAdvancement = buf.readBoolean();
            ResourceLocation advancement = null;
            if (hasAdvancement) {
                advancement = buf.readResourceLocation();
            }
            return new ShapedSoulAltarRecipe(id, i, j, nonnulllist, itemstack, flag, advancement, map);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ShapedSoulAltarRecipe recipe) {
            buf.writeVarInt(recipe.width);
            buf.writeVarInt(recipe.height);

            for(Ingredient ingredient : recipe.recipeItems) {
                ingredient.toNetwork(buf);
            }

            buf.writeItem(recipe.result);
            buf.writeBoolean(recipe.showNotification);
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