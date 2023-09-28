package com.cmdpro.spiritmancy.recipe;


import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.init.BlockInit;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.StrictNBTIngredient;


public class SoulShaperRecipe implements Recipe<Container> {
    private final ResourceLocation id;
    private final ItemStack output;
    private final Ingredient input;

    public SoulShaperRecipe(ResourceLocation id, ItemStack output,
                           Ingredient input) {
        this.id = id;
        this.output = output;
        this.input = input;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        nonnulllist.add(this.input);
        return nonnulllist;
    }
    @Override
    public boolean matches(Container pContainer, Level pLevel) {
        return input.test(pContainer.getItem(0));
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

    public static class Type implements RecipeType<SoulShaperRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "soulshaper";
    }

    public static class Serializer implements RecipeSerializer<SoulShaperRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(Spiritmancy.MOD_ID,Type.ID);

        @Override
        public SoulShaperRecipe fromJson(ResourceLocation id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new SoulShaperRecipe(id, output, input);
        }

        @Override
        public SoulShaperRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromNetwork(buf);
            ItemStack output = buf.readItem();
            return new SoulShaperRecipe(id, output, input);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, SoulShaperRecipe recipe) {
            recipe.input.toNetwork(buf);
            buf.writeItemStack(recipe.getResultItem(RegistryAccess.EMPTY), false);
        }

        @SuppressWarnings("unchecked") // Need this wrapper, because generics
        private static <G> Class<G> castClass(Class<?> cls) {
            return (Class<G>)cls;
        }
    }
}