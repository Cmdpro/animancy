package com.cmdpro.spiritmancy.recipe;


import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.init.ItemInit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;


public class SoulAltarRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> input;

    public SoulAltarRecipe(ResourceLocation id, ItemStack output,
                           NonNullList<Ingredient> input) {
        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        for (Ingredient i : this.input) {
            nonnulllist.add(i);
        }
        return nonnulllist;
    }
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        ItemStack focus = pContainer.getItem(0);
        if (focus.is(ItemInit.SOULFOCUS.get())) {
            if (focus.hasTag()) {
                if (focus.getTag().contains("recipe")) {
                    if (ResourceLocation.tryParse(focus.getTag().getString("recipe")).equals(this.getId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ItemStack assemble(Container pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }


    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<SoulAltarRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soulaltar";
    }


    public static class Serializer implements RecipeSerializer<SoulAltarRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Spiritmancy.MOD_ID, Type.ID);
        @Override
        public SoulAltarRecipe fromJson(ResourceLocation id, JsonObject json) {
            NonNullList<Ingredient> input = itemsFromJson(GsonHelper.getAsJsonArray(json, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new SoulAltarRecipe(id, output, input);
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
        public SoulAltarRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            int i = buf.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < nonnulllist.size(); ++j) {
                nonnulllist.set(j, Ingredient.fromNetwork(buf));
            }
            ItemStack output = buf.readItem();
            return new SoulAltarRecipe(id, output, nonnulllist);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulAltarRecipe recipe) {
            buf.writeVarInt(recipe.input.size());
            for(Ingredient ingredient : recipe.input) {
                ingredient.toNetwork(buf);
            }
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}