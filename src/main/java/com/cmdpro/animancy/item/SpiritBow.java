package com.cmdpro.animancy.item;

import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.entity.SpiritArrow;
import com.cmdpro.animancy.registry.EntityRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;

public class SpiritBow extends Item {

    public SpiritBow(Item.Properties pProperties) {
        super(pProperties);
    }
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild;
            ItemStack itemstack = getTank(player);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    if (!pLevel.isClientSide) {
                        AbstractArrow abstractarrow = new SpiritArrow(EntityRegistry.SPIRIT_ARROW.get(), player, pLevel);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }
                        pLevel.addFreshEntity(abstractarrow);
                    }

                    pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag) {
                        SoulTankItem.setFill(itemstack, SoulTankItem.getFillTypeLocation(itemstack), SoulTankItem.getFillNumber(itemstack)-1);
                    }
                }
            }
        }
    }
    public static float getPowerForTime(int pCharge) {
        return BowItem.getPowerForTime(pCharge);
    }
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !getTank(pPlayer).isEmpty();

        if (!pPlayer.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }
    public static ItemStack getTank(Player player) {
        List<ItemStack> slots = player.getInventory().items;
        for (ItemStack o : slots) {
            if (o.is(ItemRegistry.SOULTANK.get())) {
                float number = SoulTankItem.getFillNumber(o);
                if (number >= 1) {
                    return o;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
