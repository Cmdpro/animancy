package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.SoulTankData;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Animancy.MOD_ID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulTankData>> SOUL_TANK_DATA = DATA_COMPONENTS.registerComponentType("soul_tank_data", builder -> builder
            .persistent(SoulTankData.CODEC.codec())
            .networkSynchronized(SoulTankData.STREAM_CODEC)
            .cacheEncoding()
    );
}