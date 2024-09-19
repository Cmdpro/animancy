package com.cmdpro.animancy.item;

import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.entity.SoulProjectile;
import com.cmdpro.animancy.particle.Soul3ParticleOptions;
import com.cmdpro.animancy.particle.Soul4ParticleOptions;
import com.cmdpro.animancy.registry.EntityRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
import com.cmdpro.animancy.soultypes.SoulType;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Math;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class SoulspinStaff extends Item {
    public SoulspinStaff(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
        if (!pLivingEntity.level().isClientSide()) {
            if (pLivingEntity instanceof Player player) {
                ServerLevel serverLevel = (ServerLevel) pLevel;
                int timeUsed = getUseDuration(pStack, player) - pRemainingUseDuration;
                int soulCount = Math.clamp(0, 5, timeUsed / 10);
                List<ResourceLocation> types = new ArrayList<>();
                List<ItemStack> slots = player.getInventory().items.stream().map(ItemStack::copy).toList();
                for (int i = 0; i < soulCount; i++) {
                    for (ItemStack o : slots) {
                        if (o.is(ItemRegistry.SOULTANK.get())) {
                            float number = SoulTankItem.getFillNumber(o);
                            if (number >= 1) {
                                ResourceLocation type = SoulTankItem.getFillTypeLocation(o);
                                types.add(type);
                                SoulTankItem.setFill(o, type, number - 1);
                                break;
                            }
                        }
                    }
                }
                for (int i = 0; i < soulCount; i++) {
                    if (types.size() > i) {
                        float rot = Math.toRadians(((serverLevel.getGameTime()*5L)%360)+((360f/5f)*i));
                        float yRot = Math.toRadians(-player.getYRot());
                        Vec3 lookAngle = player.getLookAngle();
                        Vec3 perpendicular = new Vec3(-Mth.cos(yRot), 0, Mth.sin(yRot));
                        Vec3 pos = player.getEyePosition().add(perpendicular.scale(Mth.cos(rot)).add(lookAngle.cross(perpendicular).scale(Mth.sin(rot))).scale(0.6)).add(lookAngle);
                        ResourceLocation type = types.get(i);
                        serverLevel.sendParticles(new Soul4ParticleOptions(type), pos.x, pos.y, pos.z, 3, 0.05, 0.05, 0.05, 0);
                    }
                }
            }
        }
    }

    @Override
    public void onStopUsing(ItemStack stack, LivingEntity entity, int count) {
        super.onStopUsing(stack, entity, count);
        if (!entity.level().isClientSide()) {
            if (entity instanceof Player player) {
                ServerLevel serverLevel = (ServerLevel) entity.level();
                int soulCount = Math.clamp(0, 5, (getUseDuration(stack, entity)-count) / 10);
                List<ResourceLocation> types = new ArrayList<>();
                List<ItemStack> slots = player.getInventory().items;
                for (int i = 0; i < soulCount; i++) {
                    for (ItemStack o : slots) {
                        if (o.is(ItemRegistry.SOULTANK.get())) {
                            float number = SoulTankItem.getFillNumber(o);
                            if (number >= 1) {
                                ResourceLocation type = SoulTankItem.getFillTypeLocation(o);
                                types.add(type);
                                SoulTankItem.setFill(o, type, number - 1);
                                break;
                            }
                        }
                    }
                }
                for (int i = 0; i < soulCount; i++) {
                    if (types.size() > i) {
                        float rot = Math.toRadians(((serverLevel.getGameTime()*5L)%360)+((360f/5f)*i));
                        float yRot = Math.toRadians(-player.getYRot());
                        Vec3 lookAngle = player.getLookAngle();
                        Vec3 perpendicular = new Vec3(-Mth.cos(yRot), 0, Mth.sin(yRot));
                        Vec3 pos = player.getEyePosition().add(perpendicular.scale(Mth.cos(rot)).add(lookAngle.cross(perpendicular).scale(Mth.sin(rot))).scale(0.6)).add(lookAngle);
                        ResourceLocation type = types.get(i);
                        SoulProjectile projectile = new SoulProjectile(EntityRegistry.SOUL_PROJECTILE.get(), pos.x, pos.y, pos.z, entity, serverLevel);
                        projectile.type = type;
                        projectile.strength = soulCount*2;
                        serverLevel.addFreshEntity(projectile);
                    }
                }
            }
        }
    }

    public Vec3 calculateViewVector(float pXRot, float pYRot) {
        float f = pXRot * ((float) java.lang.Math.PI / 180F);
        float f1 = -pYRot * ((float) java.lang.Math.PI / 180F);
        float f2 = Mth.cos(f1);
        float f3 = Mth.sin(f1);
        float f4 = Mth.cos(f);
        float f5 = Mth.sin(f);
        return new Vec3((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
        return 72000;
    }
}
