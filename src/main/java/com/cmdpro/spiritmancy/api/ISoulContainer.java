package com.cmdpro.spiritmancy.api;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.block.entity.SoulPointBlockEntity;
import com.cmdpro.spiritmancy.init.ParticleInit;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public interface ISoulContainer {
    float getSouls();
    void setSouls(float amount);
    List<BlockPos> getLinked();
    default void drawLinked(BlockEntity entity) {
        for (BlockPos i : getLinked()) {
            drawLine(entity.getBlockPos().getCenter(), i.getCenter(), entity.getLevel(), 0.2);
        }
    }
    default void soulContainerTick(Level pLevel, BlockPos pPos, BlockState pState, BlockEntity pBlockEntity) {
        if (!pLevel.isClientSide()) {
            drawLinked(pBlockEntity);
            List<BlockPos> toRemove = new ArrayList<>();
            for (BlockPos i : getLinked()) {
                if (!(pLevel.getBlockEntity(i) instanceof ISoulContainer)) {
                    toRemove.add(i);
                }
            }
            for (BlockPos i : toRemove) {
                getLinked().remove(i);
            }
        }
    }
    static void drawLine(Vec3 point1, Vec3 point2, Level level, double space) {
        double distance = point1.distanceTo(point2);
        Vec3 vector = point2.subtract(point1).normalize().multiply(space, space, space);
        double length = 0;
        for (Vec3 point = point1; length < distance; point = point.add(vector)) {
            ((ServerLevel)level).sendParticles(ParticleInit.SOUL.get(), point.x, point.y, point.z, 1, 0, 0, 0, 0);
            length += space;
        }
    }
    default float getMaxSouls() {
        return 50;
    }
}
