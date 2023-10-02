package com.cmdpro.spiritmancy.integration;

import com.cmdpro.spiritmancy.recipe.SoulAltarRecipe;
import com.google.gson.JsonObject;
import com.klikli_dev.modonomicon.api.ModonomiconConstants.Data.Page;
import com.klikli_dev.modonomicon.book.BookTextHolder;
import com.klikli_dev.modonomicon.book.page.BookRecipePage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class BookAltarRecipePage extends BookRecipePage<Recipe<?>> {
    public BookAltarRecipePage(BookTextHolder title1, ResourceLocation recipeId1, BookTextHolder title2, ResourceLocation recipeId2, BookTextHolder text, String anchor) {
        super(SoulAltarRecipe.Type.INSTANCE, title1, recipeId1, title2, recipeId2, text, anchor);
    }

    public static BookAltarRecipePage fromJson(JsonObject json) {
        var common = BookRecipePage.commonFromJson(json);
        var anchor = GsonHelper.getAsString(json, "anchor", "");
        return new BookAltarRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    public static BookAltarRecipePage fromNetwork(FriendlyByteBuf buffer) {
        var common = BookRecipePage.commonFromNetwork(buffer);
        var anchor = buffer.readUtf();
        return new BookAltarRecipePage(common.title1(), common.recipeId1(), common.title2(), common.recipeId2(), common.text(), anchor);
    }

    @Override
    protected ItemStack getRecipeOutput(Level level, Recipe<?> recipe) {
        if (recipe == null) {
            return ItemStack.EMPTY;
        }

        return recipe.getResultItem(level.registryAccess());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer) {
        super.toNetwork(buffer);
        buffer.writeUtf(this.anchor);
    }

    @Override
    public ResourceLocation getType() {
        return SpiritmancyModonomiconConstants.Page.ALTAR_RECIPE;
    }
}