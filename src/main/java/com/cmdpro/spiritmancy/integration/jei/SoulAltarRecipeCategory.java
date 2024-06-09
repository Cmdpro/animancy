package com.cmdpro.spiritmancy.integration.jei;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.init.ItemInit;
import com.cmdpro.spiritmancy.recipe.ISoulAltarRecipe;
import com.cmdpro.spiritmancy.soultypes.SoulType;
import com.cmdpro.spiritmancy.soultypes.SoulTypeManager;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.ICraftingGridHelper;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.crafting.IShapedRecipe;

import java.util.List;
import java.util.Map;

public class SoulAltarRecipeCategory implements IRecipeCategory<ISoulAltarRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Spiritmancy.MOD_ID, "soulaltar");
    public final static ResourceLocation TEXTURE =
            new ResourceLocation(Spiritmancy.MOD_ID, "textures/gui/jeicrafting.png");
    private final IDrawable background;
    private final IDrawable icon;
    private final ICraftingGridHelper craftingGridHelper;

    public SoulAltarRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createDrawable(TEXTURE, 0, 0, 116, 74);
        this.craftingGridHelper = guiHelper.createCraftingGridHelper();
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ItemInit.SOULALTARITEM.get()));
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ISoulAltarRecipe recipe, IFocusGroup focuses) {
        // Initialize recipe output
        this.craftingGridHelper.createAndSetOutputs(builder, VanillaTypes.ITEM_STACK, List.of(recipe.getResultItem(Minecraft.getInstance().level.registryAccess())));

        // Initialize recipe inputs
        int width = (recipe instanceof IShapedRecipe<?> shapedRecipe) ? shapedRecipe.getRecipeWidth() : 0;
        int height = (recipe instanceof IShapedRecipe<?> shapedRecipe) ? shapedRecipe.getRecipeHeight() : 0;
        List<List<ItemStack>> inputs = recipe.getIngredients().stream().map(ingredient -> List.of(ingredient.getItems())).toList();
        this.craftingGridHelper.createAndSetInputs(builder, VanillaTypes.ITEM_STACK, inputs, width, height);
    }
    @Override
    public void draw(ISoulAltarRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        if (recipe.getSoulCost() != null) {
            int x = 41-(int)((recipe.getSoulCost().size()*18f)/2f);
            int y = 58;
            for (Map.Entry<ResourceLocation, Float> i : recipe.getSoulCost().entrySet()) {
                SoulType type = SoulTypeManager.types.get(i.getKey());
                if (type != null) {
                    guiGraphics.blit(type.icon, x, y, 0, 0, 16, 16);
                    guiGraphics.drawCenteredString(Minecraft.getInstance().font, i.getValue().toString(), x+8, y+8, 0xFFFFFFFF);
                }
                x += 18;
            }
        }
    }

    @Override
    public RecipeType<ISoulAltarRecipe> getRecipeType() {
        return JEIRunologyPlugin.SOULALTAR;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.spiritmancy.soulaltar");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

}