package com.cmdpro.animancy.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SoulTankData(float fill, Optional<ResourceLocation> type) {
    public static final MapCodec<SoulTankData> CODEC = RecordCodecBuilder.mapCodec((codec) ->
            codec.group(
                    Codec.FLOAT.fieldOf("fill").forGetter((data) -> data.fill),
                    ResourceLocation.CODEC.optionalFieldOf("type").forGetter((data) -> data.type)
            ).apply(codec, SoulTankData::new)
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, SoulTankData> STREAM_CODEC = StreamCodec.of((pBuffer, pValue) -> {
        pBuffer.writeFloat(pValue.fill);
        pBuffer.writeOptional(pValue.type, ResourceLocation.STREAM_CODEC);
    }, (pBuffer) -> {
        float fill = pBuffer.readFloat();
        Optional<ResourceLocation> type = pBuffer.readOptional(ResourceLocation.STREAM_CODEC);
        return new SoulTankData(fill, type);
    });
}