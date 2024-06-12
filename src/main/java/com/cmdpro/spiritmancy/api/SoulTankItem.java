package com.cmdpro.spiritmancy.api;

import com.cmdpro.spiritmancy.soultypes.SoulType;
import com.cmdpro.spiritmancy.soultypes.SoulTypeManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;

import java.util.List;

public abstract class SoulTankItem extends Item {
    public SoulTankItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("item.spiritmancy.soultank.tooltip", getFillNumber(pStack), getMaxSouls()));
        SoulType type = getFillType(pStack);
        if (type != null) {
            pTooltipComponents.add(Component.translatable("item.spiritmancy.soultank.tooltip2", type.name).withStyle(Style.EMPTY.withColor(type.color.getRGB())));
        }
    }

    public static float getFillNumber(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.getTag().contains("fill")) {
                return stack.getTag().getFloat("fill");
            }
        }
        return 0;
    }
    public static SoulType getFillType(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.hasTag() && stack.getTag().contains("fillType")) {
                ResourceLocation fillType = ResourceLocation.tryParse(stack.getTag().getString("fillType"));
                SoulType soulType = SoulTypeManager.types.get(fillType);
                return soulType;
            }
        }
        return null;
    }
    public static ResourceLocation getFillTypeLocation(ItemStack stack) {
        if (stack.hasTag()) {
            if (stack.hasTag() && stack.getTag().contains("fillType")) {
                ResourceLocation fillType = ResourceLocation.tryParse(stack.getTag().getString("fillType"));
                return fillType;
            }
        }
        return null;
    }
    public static float getFill(ItemStack stack) {
        if (stack.getItem() instanceof SoulTankItem tank) {
            if (stack.hasTag()) {
                if (stack.getTag().contains("fill")) {
                    return stack.getTag().getFloat("fill")/tank.getMaxSouls();
                }
            }
        }
        return 0;
    }
    public static void setFill(ItemStack stack, ResourceLocation type, float amount) {
        stack.getOrCreateTag().putFloat("fill", amount);
        if (type == null) {
            stack.getOrCreateTag().putString("fillType", "");
        } else {
            stack.getOrCreateTag().putString("fillType", type.toString());
        }
    }
    public static boolean addFill(ItemStack stack, ResourceLocation type, float amount) {
        if (stack.getItem() instanceof SoulTankItem tank) {
            ResourceLocation stackType = getFillTypeLocation(stack);
            if (!stack.hasTag() || !stack.getTag().contains("fill") || stackType == null || stackType.equals(type)) {
                if (!stack.getOrCreateTag().contains("fillType")) {
                    stack.getOrCreateTag().putString("fillType", type.toString());
                }
                stack.getOrCreateTag().putFloat("fill", Math.clamp(Float.MIN_VALUE, tank.getMaxSouls(), SoulTankItem.getFillNumber(stack) + amount));
                return true;
            }
        }
        return false;
    }
    public static float removeFill(ItemStack stack, ResourceLocation type, float amount) {
        return removeFill(stack, type, amount, true);
    }
    public static float removeFill(ItemStack stack, ResourceLocation type, float amount, boolean actuallyRemove) {
        ResourceLocation stackType = getFillTypeLocation(stack);
        if (!stack.hasTag() || !stack.getTag().contains("fill") || stackType == null || stackType.equals(type)) {
            float changed = SoulTankItem.getFillNumber(stack)-amount;
            if (actuallyRemove) {
                if (!stack.getOrCreateTag().contains("fillType")) {
                    stack.getOrCreateTag().putString("fillType", type.toString());
                }
                stack.getOrCreateTag().putFloat("fill", Math.clamp(0, Float.MAX_VALUE, changed));
                if (changed <= 0) {
                    if (stack.hasTag()) {
                        if (stack.getTag().contains("fillType")) {
                            stack.getTag().remove("fillType");
                        }
                    }
                }
            }
            return Math.clamp(0, Float.MAX_VALUE, -changed);
        }
        return -1;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.isShiftKeyDown()) {
            setFill(pPlayer.getItemInHand(pUsedHand), null, 0);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public abstract float getMaxSouls();
}
