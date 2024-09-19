package com.cmdpro.animancy.api;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public record SoulboundLocationData(Optional<ResourceKey<Level>> level, Optional<BlockPos> location) {
    public static final MapCodec<SoulboundLocationData> CODEC = RecordCodecBuilder.mapCodec(codec ->
            codec.group(
                    ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("level").forGetter((data) -> data.level),
                    BlockPos.CODEC.optionalFieldOf("location").forGetter((data) -> data.location)
            ).apply(codec, SoulboundLocationData::new)
    );
}
