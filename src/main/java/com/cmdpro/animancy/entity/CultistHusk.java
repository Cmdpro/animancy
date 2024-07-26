package com.cmdpro.animancy.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;


public class CultistHusk extends Monster implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    public CultistHusk(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 3f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.2f).build();
    }
    private <E extends GeoAnimatable> PlayState predicate(AnimationState event) {
        if (event.isMoving()) {
            event.getController().setAnimation(RawAnimation.begin().then("animation.cultist_husk.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(RawAnimation.begin().then("animation.cultist_husk.idle", Animation.LoopType.LOOP));
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
        data.add(new AnimationController(this, "controller", 20, this::predicate));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }


    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        playSound(SoundEvents.ZOMBIE_STEP);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ZOMBIE_DEATH;
    }
    protected float getSoundVolume() {
        return 0.2F;
    }
}
