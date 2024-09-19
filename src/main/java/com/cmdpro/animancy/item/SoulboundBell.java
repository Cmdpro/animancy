package com.cmdpro.animancy.item;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.api.SoulboundLocationData;
import com.cmdpro.animancy.registry.AttachmentTypeRegistry;
import com.cmdpro.animancy.registry.BlockRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.arguments.blocks.BlockInput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.apache.commons.lang3.RandomUtils;
import org.joml.Math;

import java.util.List;
import java.util.function.Consumer;

public class SoulboundBell extends Item {
    public SoulboundBell(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pLivingEntity) {
        return 38;
    }
    @Override
    @SuppressWarnings("removal")
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
                    float f = (((float)itemInHand.getUseDuration(player)-(float)player.getUseItemRemainingTicks()) + partialTick);
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
        if (!pLevel.isClientSide) {
            SoulboundLocationData data = pPlayer.getData(AttachmentTypeRegistry.SOULBOUND_LOCATION);
            if (data.level().isPresent() && data.location().isPresent()) {
                pPlayer.sendSystemMessage(Component.translatable("item.animancy.soulbound_bell.sending_to", data.location().get().getX(), data.location().get().getY(), data.location().get().getZ()));
            }
        }
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
                int souls = 0;
                for (ItemStack o : player.getInventory().items) {
                    if (o.is(ItemRegistry.SOULTANK.get())) {
                        float number = SoulTankItem.getFillNumber(o);
                        ResourceLocation type = SoulTankItem.getFillTypeLocation(o);
                        if (type.equals(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "purified"))) {
                            souls += number;
                        }
                    }
                }
                if (souls >= 5) {
                    SoulboundLocationData data = player.getData(AttachmentTypeRegistry.SOULBOUND_LOCATION);
                    if (data.level().isPresent() && data.location().isPresent()) {
                        if (pLevel.dimension().equals(data.level().get())) {
                            if (pLevel.getBlockState(data.location().get().below()).is(BlockRegistry.SPIRITUAL_ANCHOR.get())) {
                                int remaining = 5;
                                for (ItemStack o : player.getInventory().items) {
                                    if (o.is(ItemRegistry.SOULTANK.get())) {
                                        float number = SoulTankItem.getFillNumber(o);
                                        ResourceLocation type = SoulTankItem.getFillTypeLocation(o);
                                        if (type.equals(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "purified"))) {
                                            SoulTankItem.setFill(o, type, number - Math.min(number, remaining));
                                            remaining -= number;
                                            if (remaining <= 0) {
                                                break;
                                            }
                                        }
                                    }
                                }
                                Vec3 pos = data.location().get().getCenter();
                                player.teleportTo(pos.x, pos.y, pos.z);
                            } else {
                                player.sendSystemMessage(Component.translatable("item.animancy.soulbound_bell.fail_broken"));
                            }
                        } else {
                            player.sendSystemMessage(Component.translatable("item.animancy.soulbound_bell.fail_dimension"));
                        }
                    } else {
                        player.sendSystemMessage(Component.translatable("item.animancy.soulbound_bell.fail_not_bound"));
                    }
                } else {
                    player.sendSystemMessage(Component.translatable("item.animancy.soulbound_bell.fail_not_enough_souls"));
                }
                player.getCooldowns().addCooldown(this, 20);
            }
        }
        return pStack;
    }
}
