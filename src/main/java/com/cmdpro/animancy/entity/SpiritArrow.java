package com.cmdpro.animancy.entity;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.ParticleRegistry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SpiritArrow extends AbstractArrow {
    public SpiritArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SpiritArrow(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public SpiritArrow(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            ((ServerLevel) level()).sendParticles(ParticleRegistry.SOUL.get(), pResult.getLocation().x, pResult.getLocation().y + 1f, pResult.getLocation().z, 100, 0, 0, 0, 0.75f);
            level().playSound(null, pResult.getLocation().x, pResult.getLocation().y, pResult.getLocation().z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.0f, 1.0f);
            for (LivingEntity i : level().getEntitiesOfClass(LivingEntity.class, AABB.ofSize(pResult.getLocation(), 6, 6, 6))) {
                i.hurt(i.damageSources().source(Animancy.soulExplosion), 4);
            }
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected boolean tryPickup(Player pPlayer) {
        return true;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }
}
