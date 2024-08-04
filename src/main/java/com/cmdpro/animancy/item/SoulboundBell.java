package com.cmdpro.animancy.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.apache.commons.lang3.RandomUtils;
import org.joml.Math;

import java.util.function.Consumer;

public class SoulboundBell extends Item {
    public SoulboundBell(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 38;
    }
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    float f = (((float)itemInHand.getUseDuration()-(float)player.getUseItemRemainingTicks()) + partialTick);
                    poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.sin(Math.toRadians(Math.clamp(0f, 36f, f) * (360f / 12f))) * 25f));
                    applyItemArmTransform(poseStack, arm, equipProcess);
                    return true;
                } else {
                    return false;
                }
            }
            private void applyItemArmTransform(PoseStack pPoseStack, HumanoidArm pHand, float pEquippedProg) {
                int i = pHand == HumanoidArm.RIGHT ? 1 : -1;
                pPoseStack.translate((float)i * 0.56F, -0.52F + pEquippedProg * -0.6F, -0.72F);
            }
        });
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        pPlayer.startUsingItem(pUsedHand);
        return InteractionResultHolder.consume(stack);
    }
    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        if (!pLevel.isClientSide) {
            if (pRemainingUseDuration % 12 == 0) {
                pLevel.playSound(null, pLivingEntity.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS);
            }
            if (pRemainingUseDuration % 12 == 6) {
                pLevel.playSound(null, pLivingEntity.blockPosition(), SoundEvents.BELL_BLOCK, SoundSource.PLAYERS, 1.0f, 0.75f);
            }
        }
    }
    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        if (!pLevel.isClientSide) {
            if (pLivingEntity instanceof Player player) {
                Component msg = Component.literal("Test");
                player.sendSystemMessage(msg);
                player.getCooldowns().addCooldown(this, 20);
            }
        }
        return pStack;
    }
}
