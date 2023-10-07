package com.cmdpro.spiritmancy.entity;

import com.cmdpro.spiritmancy.init.ParticleInit;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.util.thread.EffectiveSide;
import org.apache.commons.lang3.RandomUtils;
import org.lwjgl.system.MathUtil;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class SoulKeeper extends Monster implements GeoEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS));
    public SoulKeeper(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 500.0D)
                .add(Attributes.ATTACK_DAMAGE, 5f)
                .add(Attributes.ATTACK_SPEED, 2f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }
    @Override
    public void setCustomName(@Nullable Component p_31476_) {
        super.setCustomName(p_31476_);
        this.bossEvent.setName(this.getDisplayName());
    }
    public int spawnAnimTimer = 0;
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("spawnanimtimer", spawnAnimTimer);
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        spawnAnimTimer = tag.getInt("spawnanimtimer");
        if (this.hasCustomName()) {
            this.bossEvent.setName(this.getDisplayName());
        }
    }
    @Override
    public void startSeenByPlayer(ServerPlayer p_31483_) {
        super.startSeenByPlayer(p_31483_);
        this.bossEvent.addPlayer(p_31483_);
    }
    @Override
    public void stopSeenByPlayer(ServerPlayer p_31488_) {
        super.stopSeenByPlayer(p_31488_);
        this.bossEvent.removePlayer(p_31488_);
    }

    public int atkTimer;
    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        if (spawnAnimTimer < 200) {
            atkTimer += 1;
            if (atkTimer >= 250) {
                atkTimer = 0;
                int atk = random.nextInt(0, 4);

            }
        }
    }
    private static final EntityDataAccessor<Boolean> IS_SPAWN_ANIM = SynchedEntityData.defineId(SoulKeeper.class, EntityDataSerializers.BOOLEAN);
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_SPAWN_ANIM, true);
    }
    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide) {
            if (entityData.get(IS_SPAWN_ANIM)) {
                for (int i = 0; i < 5; i++) {
                    Vec3 offset = new Vec3(RandomUtils.nextDouble(0, 4) - 2d, RandomUtils.nextDouble(0, 4) - 2d, RandomUtils.nextDouble(0, 4) - 2d);
                    Vec3 pos = position().add(0, 1, 0).add(offset);
                    level().addParticle(ParticleInit.SOUL.get(), pos.x, pos.y, pos.z, -offset.x / 4f, -offset.y / 4f, -offset.z / 4f);
                }
            }
        } else {
            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
            if (spawnAnimTimer <= 0) {
                entityData.set(IS_SPAWN_ANIM, false);
                setNoAi(false);
            } else {
                setNoAi(true);
                entityData.set(IS_SPAWN_ANIM, true);
                setHealth(((200-spawnAnimTimer)*(getMaxHealth()/200f))+1);
                spawnAnimTimer--;
            }
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (spawnAnimTimer > 0) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState event) {
        if (entityData.get(IS_SPAWN_ANIM)) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.soulkeeper.idle", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.soulkeeper.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().then("animation.soulkeeper.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController(this, "controller", 0, this::predicate));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {

    }

    protected SoundEvent getAmbientSound() {
        return null;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PLAYER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PLAYER_DEATH;
    }
    protected float getSoundVolume() {
        return 0.2F;
    }

    public void spawn() {
        bossEvent.setProgress(0);
        setHealth(1);
        spawnAnimTimer = 200;
    }
}
