package com.cmdpro.animancy.particle;

import com.cmdpro.animancy.registry.ParticleRegistry;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;
import java.util.UUID;

public class Soul4ParticleOptions implements ParticleOptions {
    public ResourceLocation soulType;
    public Soul4ParticleOptions(ResourceLocation soulType) {
        this.soulType = soulType;
    }
    @Override
    public ParticleType<?> getType() {
        return ParticleRegistry.SOUL4.get();
    }

    public static final MapCodec<Soul4ParticleOptions> CODEC = RecordCodecBuilder.mapCodec((builder) -> {
        return builder.group(ResourceLocation.CODEC.fieldOf("type").forGetter((particle) -> {
            return particle.soulType;
        })).apply(builder, (type) -> new Soul4ParticleOptions(type));
    });

    public static final StreamCodec<RegistryFriendlyByteBuf, Soul4ParticleOptions> STREAM_CODEC = StreamCodec.of((buf, options) -> {
        buf.writeResourceLocation(options.soulType);
    }, (buf) -> {
        ResourceLocation type = buf.readResourceLocation();
        return new Soul4ParticleOptions(type);
    });
}