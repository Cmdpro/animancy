package com.cmdpro.animancy.entity;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.AnimancyUtil;
import com.cmdpro.animancy.registry.ParticleRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.apache.commons.lang3.RandomUtils;
import vazkii.patchouli.api.IMultiblock;
import vazkii.patchouli.api.PatchouliAPI;

public class SoulRitualController extends Entity {
    public int time;
    public SoulRitualController(EntityType<SoulRitualController> entityType, Level world) {
        super(entityType, world);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("time", time);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        time = tag.getInt("time");
    }

    @Override
    protected void defineSynchedData() {

    }
    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes().build();
    }

    public void soulEffect(Vec3 pos) {
        ((ServerLevel)level()).sendParticles(ParticleTypes.SCULK_SOUL, pos.x, pos.y, pos.z, 15, 0, 0, 0, 0.1);
        level().playSound(null, pos.x, pos.y, pos.z, SoundEvents.SCULK_SHRIEKER_SHRIEK, SoundSource.BLOCKS, 1, 1);
    }

    public boolean isRitualFine() {
        IMultiblock ritual = PatchouliAPI.get().getMultiblock(new ResourceLocation(Animancy.MOD_ID, "soulritualnoflames"));
        if (ritual != null) {
            return ritual.validate(level(), blockPosition().below(), Rotation.NONE) &&
                    ritual.validate(level(), blockPosition().below(), Rotation.CLOCKWISE_90) &&
                    ritual.validate(level(), blockPosition().below(), Rotation.CLOCKWISE_180) &&
                    ritual.validate(level(), blockPosition().below(), Rotation.COUNTERCLOCKWISE_90);
        }
        return false;
    }
    @Override
    public void tick() {
        time++;
        if (!level().isClientSide) {
            if (!isRitualFine()) {
                remove(RemovalReason.DISCARDED);
            }
            if (time == 20) {
                Vec3 pos = blockPosition().getCenter().subtract(0, 0.5f, 0);
                soulEffect(pos);
                level().removeBlock(blockPosition(), false);
            }
            if (time == 50) {
                AnimancyUtil.spawnSoulKeeper(blockPosition().getCenter().add(0, 4, 0), level());
            }
            if (time >= 50) {
                remove(RemovalReason.DISCARDED);
            }
        } else {
            if (time >= 20) {
                for (int i = 0; i < 5; i++) {
                    Vec3 offset = new Vec3(RandomUtils.nextDouble(0, 1) - 0.5d, 0, RandomUtils.nextDouble(0, 1) - 0.5d);
                    Vec3 pos = blockPosition().getCenter().subtract(0, 0.5f, 0).add(offset);
                    level().addParticle(ParticleRegistry.SOUL.get(), pos.x+offset.x, pos.y, pos.z+offset.y, -offset.x / 4f, 1, -offset.z / 4f);
                }
            }
        }
        super.tick();
    }
    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
