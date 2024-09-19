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
import net.minecraft.util.ExtraCodecs;

import java.awt.*;
import java.util.Locale;
import java.util.UUID;

public class Soul3ParticleOptions implements ParticleOptions {
    public UUID player;
    public ResourceLocation soulType;
    public Soul3ParticleOptions(UUID player, ResourceLocation soulType) {
        this.player = player;
        this.soulType = soulType;
    }
    @Override
    public ParticleType<?> getType() {
        return ParticleRegistry.SOUL3.get();
    }

    public static final MapCodec<Soul3ParticleOptions> CODEC = RecordCodecBuilder.mapCodec((builder) -> {
        return builder.group(Codec.STRING.fieldOf("uuid").forGetter((particle) -> {
            return particle.player.toString();
        }), ResourceLocation.CODEC.fieldOf("type").forGetter((particle) -> {
            return particle.soulType;
        })).apply(builder, (uuid, type) -> new Soul3ParticleOptions(UUID.fromString(uuid), type));
    });

    public static final StreamCodec<RegistryFriendlyByteBuf, Soul3ParticleOptions> STREAM_CODEC = StreamCodec.of((buf, options) -> {
        buf.writeUUID(options.player);
        buf.writeResourceLocation(options.soulType);
    }, (buf) -> {
        UUID uuid = buf.readUUID();
        ResourceLocation type = buf.readResourceLocation();
        return new Soul3ParticleOptions(uuid, type);
    });
}