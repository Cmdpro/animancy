package com.cmdpro.spiritmancy.integration;

import com.cmdpro.spiritmancy.init.RecipeInit;
import com.cmdpro.spiritmancy.recipe.ISoulAltarRecipe;
import com.cmdpro.spiritmancy.recipe.ShapedSoulAltarRecipe;
import com.cmdpro.spiritmancy.renderers.SoulAltarRenderer;
import com.cmdpro.spiritmancy.soultypes.SoulType;
import com.cmdpro.spiritmancy.soultypes.SoulTypeManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;

import vazkii.patchouli.client.book.gui.GuiBook;
import vazkii.patchouli.client.book.page.abstr.PageDoubleRecipeRegistry;

import java.util.Map;

public class PageSoulAltar extends PageDoubleRecipeRegistry<Recipe<?>> {

	public PageSoulAltar() {
		super(RecipeInit.SOULALTAR.get());
	}

	@Override
	protected void drawRecipe(GuiGraphics graphics, Recipe<?> recipe, int recipeX, int recipeY, int mouseX, int mouseY, boolean second) {
		Level level = Minecraft.getInstance().level;
		if (level == null) {
			return;
		}

		RenderSystem.enableBlend();
		graphics.blit(book.craftingTexture, recipeX - 2, recipeY - 2, 0, 0, 100, 62, 128, 256);

		boolean shaped = recipe instanceof ShapedSoulAltarRecipe;
		if (!shaped) {
			int iconX = recipeX + 62;
			int iconY = recipeY + 2;
			graphics.blit(book.craftingTexture, iconX, iconY, 0, 64, 11, 11, 128, 256);
			if (parent.isMouseInRelativeRange(mouseX, mouseY, iconX, iconY, 11, 11)) {
				parent.setTooltip(Component.translatable("patchouli.gui.lexicon.shapeless"));
			}
		}

		parent.drawCenteredStringNoShadow(graphics, getTitle(second).getVisualOrderText(), GuiBook.PAGE_WIDTH / 2, recipeY - 10, book.headerColor);

		parent.renderItemStack(graphics, recipeX + 79, recipeY + 22, mouseX, mouseY, recipe.getResultItem(level.registryAccess()));

		NonNullList<Ingredient> ingredients = recipe.getIngredients();
		int wrap = 3;
		if (shaped) {
			wrap = ((ShapedSoulAltarRecipe) recipe).getWidth();
		}

		for (int i = 0; i < ingredients.size(); i++) {
			parent.renderIngredient(graphics, recipeX + (i % wrap) * 19 + 3, recipeY + (i / wrap) * 19 + 3, mouseX, mouseY, ingredients.get(i));
		}

		parent.renderItemStack(graphics, recipeX + 79, recipeY + 41, mouseX, mouseY, recipe.getToastSymbol());
		if (recipe instanceof ISoulAltarRecipe recipe3) {
			int x = recipeX+41-(int)((recipe3.getSoulCost().size()*18f)/2f);
			int y = recipeY+79;
			for (Map.Entry<ResourceLocation, Float> i : recipe3.getSoulCost().entrySet()) {
				SoulType type = SoulTypeManager.types.get(i.getKey());
				if (type != null) {
					graphics.blit(type.icon, x, y, 0, 0, 16, 16);
					graphics.drawCenteredString(Minecraft.getInstance().font, i.getValue().toString(), x+9, y+9, 0xFFFFFFFF);
				}
				x += 18;
			}
		}
	}

	@Override
	protected int getRecipeHeight() {
		return 96;
	}

	@Override
	protected ItemStack getRecipeOutput(Level level, Recipe<?> recipe) {
		if (recipe == null || level == null) {
			return ItemStack.EMPTY;
		}

		return recipe.getResultItem(level.registryAccess());
	}

}
