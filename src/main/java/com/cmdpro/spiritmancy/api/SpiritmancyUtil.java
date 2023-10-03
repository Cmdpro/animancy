package com.cmdpro.spiritmancy.api;

import com.cmdpro.spiritmancy.init.SoulcasterEffectInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Map;
import java.util.function.Supplier;

public class SpiritmancyUtil {
    public static Supplier<IForgeRegistry<SoulcasterEffect>> SOULCASTER_EFFECTS_REGISTRY = null;
}
