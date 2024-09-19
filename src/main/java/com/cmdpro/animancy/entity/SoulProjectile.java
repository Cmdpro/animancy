package com.cmdpro.animancy.entity;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.particle.Soul4ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.apache.commons.lang3.RandomUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Objects;

public class SoulProjectile extends Projectile {
    public int time;
    public ResourceLocation type;
    public float strength;
    public SoulProjectile(EntityType<SoulProjectile> entityType, Level world) {
        super(entityType, world);
    }
    protected SoulProjectile(EntityType<SoulProjectile> entityType, double x, double y, double z, Level world) {
        this(entityType, world);
        this.setPos(x, y, z);
    }
    public SoulProjectile(EntityType<SoulProjectile> entityType, double x, double y, double z, LivingEntity shooter, Level world) {
        this(entityType, x, y, z, world);
        this.setOwner(shooter);
        this.setDeltaMovement(shooter.getLookAngle().multiply(0.5, 0.5, 0.5));
    }
    @Override
    protected void onHitBlock(BlockHitResult hit) {
        remove(RemovalReason.KILLED);
        super.onHitBlock(hit);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("time", (int)this.time);
        tag.putString("type", type.toString());
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.time = tag.getInt("time");
        this.type = ResourceLocation.tryParse(tag.getString("type"));
    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 p_36758_, Vec3 p_36759_) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, p_36758_, p_36759_, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    private static final EntityDataAccessor<String> TYPE = SynchedEntityData.defineId(SoulProjectile.class, EntityDataSerializers.STRING);
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TYPE, "");
    }
    Vec3 previousPos;
    @Override
    public void tick() {
        previousPos = position();
        this.setPos(this.getX() + getDeltaMovement().x, this.getY() + getDeltaMovement().y, this.getZ() + getDeltaMovement().z);
        if (!level().isClientSide) {
            entityData.set(TYPE, type.toString());
            time++;
            if (time >= 100) {
                remove(RemovalReason.KILLED);
            }
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            while(!this.isRemoved()) {
                EntityHitResult entityhitresult = this.findHitEntity(this.position(), this.position().add(getDeltaMovement()));
                if (entityhitresult != null) {
                    hitresult = entityhitresult;
                }

                if (hitresult != null && hitresult.getType() == HitResult.Type.ENTITY) {
                    Entity entity = ((EntityHitResult)hitresult).getEntity();
                    Entity entity1 = this.getOwner();
                    if ((entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity))) {
                        hitresult = null;
                        entityhitresult = null;
                    }
                }

                if (hitresult != null && hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
                    this.onHit(hitresult);
                    this.hasImpulse = true;
                }

                if (entityhitresult == null) {
                    break;
                }

                hitresult = null;
            }

        } else {
            for (double i = 0; i < 3; i++) {
                for (int o = 0; o < 2; o++) {
                    Vec3 pos = position();
                    pos = pos.subtract(getDeltaMovement().x*(i/3d), getDeltaMovement().y*(i/3d), getDeltaMovement().z*(i/3d));
                    pos = pos.add(0, getBoundingBox().getYsize() / 2f, 0);
                    pos = pos.add(RandomUtils.nextFloat(0, 0.2f) - 0.1f, RandomUtils.nextFloat(0, 0.2f) - 0.1f, RandomUtils.nextFloat(0, 0.2f) - 0.1f);
                    ClientMethods.particle(pos, level(), entityData.get(TYPE));
                }
            }
        }
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit) {
        super.onHitEntity(hit);
        Entity entity = this.getOwner();
        DamageSource damagesource;
        if (entity instanceof LivingEntity) {
            damagesource = this.damageSources().source(Animancy.magicProjectile, this, entity);
            ((LivingEntity)entity).setLastHurtMob(hit.getEntity());
        } else if (entity instanceof Player) {
            damagesource = this.damageSources().source(Animancy.magicProjectile, this, entity);
            ((LivingEntity)entity).setLastHurtMob(hit.getEntity());
        } else {
            damagesource = null;
        }
        boolean flag = hit.getEntity().getType() == EntityType.ENDERMAN;
        if (damagesource != null) {
            if (hit.getEntity() instanceof LivingEntity ent && entity instanceof LivingEntity) {
                if (ent.hurt(damagesource, strength)) {
                    if (flag) {
                        return;
                    }
                }
            }
        }
        remove(RemovalReason.KILLED);
    }
    public static class ClientMethods {
        public static void particle(Vec3 pos, Level level, String type) {
            if (!type.isEmpty()) {
                level.addParticle(new Soul4ParticleOptions(ResourceLocation.tryParse(type)), pos.x, pos.y, pos.z, 0, 0, 0);
            }
        }
    }
}
