package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.particle.Soul3ParticleOptions;
import com.cmdpro.animancy.particle.Soul4ParticleOptions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Animancy.MOD_ID);

    public static final Supplier<SimpleParticleType> SOUL =
            register("soul", () -> new SimpleParticleType(true));
    public static final Supplier<SimpleParticleType> SOUL2 =
            register("soul2", () -> new SimpleParticleType(true));
    public static final Supplier<ParticleType> SOUL3 =
            register("soul3", () -> new ParticleType<Soul3ParticleOptions>(false) {
                @Override
                public MapCodec<Soul3ParticleOptions> codec() {
                    return Soul3ParticleOptions.CODEC;
                }

                @Override
                public StreamCodec<? super RegistryFriendlyByteBuf, Soul3ParticleOptions> streamCodec() {
                    return Soul3ParticleOptions.STREAM_CODEC;
                }
            });
    public static final Supplier<ParticleType> SOUL4 =
            register("soul4", () -> new ParticleType<Soul4ParticleOptions>(false) {
                @Override
                public MapCodec<Soul4ParticleOptions> codec() {
                    return Soul4ParticleOptions.CODEC;
                }

                @Override
                public StreamCodec<? super RegistryFriendlyByteBuf, Soul4ParticleOptions> streamCodec() {
                    return Soul4ParticleOptions.STREAM_CODEC;
                }
            });

    private static <T extends ParticleType> Supplier<T> register(final String name, final Supplier<T> item) {
        return PARTICLE_TYPES.register(name, item);
    }
}
