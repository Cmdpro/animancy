package com.cmdpro.animancy.api;

import com.cmdpro.animancy.registry.DataComponentRegistry;
import com.cmdpro.animancy.soultypes.SoulType;
import com.cmdpro.animancy.soultypes.SoulTypeManager;
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
import java.util.Optional;

public abstract class SoulTankItem extends Item {
    public SoulTankItem(Properties pProperties) {
        super(pProperties.stacksTo(1));
    }


    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatable("item.animancy.soul_tank.tooltip", getFillNumber(pStack), getMaxSouls()));
        SoulType type = getFillType(pStack);
        if (type != null) {
            pTooltipComponents.add(Component.translatable("item.animancy.soul_tank.tooltip2", type.name).withStyle(Style.EMPTY.withColor(type.color.getRGB())));
        }
    }

    public static SoulTankData getData(ItemStack stack) {
        return stack.getOrDefault(DataComponentRegistry.SOUL_TANK_DATA, new SoulTankData(0, Optional.empty()));
    }
    public static float getFillNumber(ItemStack stack) {
        SoulTankData data = getData(stack);
        return data.fill();
    }
    public static SoulType getFillType(ItemStack stack) {
        SoulTankData data = getData(stack);
        if (data.type().isPresent()) {
            SoulType soulType = SoulTypeManager.types.get(data.type().get());
            return soulType;
        }
        return null;
    }
    public static ResourceLocation getFillTypeLocation(ItemStack stack) {
        SoulTankData data = getData(stack);
        if (data.type().isPresent()) {
            return data.type().get();
        }
        return null;
    }
    public static float getFill(ItemStack stack) {
        if (stack.getItem() instanceof SoulTankItem tank) {
            SoulTankData data = getData(stack);
            return data.fill() / tank.getMaxSouls();
        }
        return 0;
    }
    public static void setFill(ItemStack stack, ResourceLocation type, float amount) {
        stack.set(DataComponentRegistry.SOUL_TANK_DATA, new SoulTankData(amount, Optional.of(type)));
    }
    public static boolean addFill(ItemStack stack, ResourceLocation type, float amount) {
        SoulTankData data = getData(stack);
        if (stack.getItem() instanceof SoulTankItem tank) {
            Optional<ResourceLocation> type2 = data.type();
            float amount2 = Math.clamp(Float.MIN_VALUE, tank.getMaxSouls(), SoulTankItem.getFillNumber(stack) + amount);
            if (type2.isEmpty() || type2.get().equals(type)) {
                stack.set(DataComponentRegistry.SOUL_TANK_DATA, new SoulTankData(amount2, Optional.of(type)));
                return true;
            }
        }
        return false;
    }
    public static float removeFill(ItemStack stack, ResourceLocation type, float amount) {
        return removeFill(stack, type, amount, true);
    }
    public static float removeFill(ItemStack stack, ResourceLocation type, float amount, boolean actuallyRemove) {
        SoulTankData data = getData(stack);
        Optional<ResourceLocation> type2 = data.type();
        float amount2;
        if (type2.isEmpty() || type2.get().equals(type)) {
            float changed = SoulTankItem.getFillNumber(stack) - amount;
            if (actuallyRemove) {
                type2 = Optional.of(type);
                amount2 = Math.clamp(0, Float.MAX_VALUE, changed);
                if (changed <= 0) {
                    type2 = Optional.empty();
                }
                stack.set(DataComponentRegistry.SOUL_TANK_DATA, new SoulTankData(amount2, type2));
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
