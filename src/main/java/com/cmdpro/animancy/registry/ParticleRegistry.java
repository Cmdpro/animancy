package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.particle.Soul3ParticleOptions;
import com.cmdpro.animancy.particle.Soul4ParticleOptions;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Animancy.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SOUL =
            PARTICLE_TYPES.register("soul", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SOUL2 =
            PARTICLE_TYPES.register("soul2", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType> SOUL3 =
            PARTICLE_TYPES.register("soul3", () -> new ParticleType<Soul3ParticleOptions>(false, Soul3ParticleOptions.DESERIALIZER) {
                @Override
                public Codec<Soul3ParticleOptions> codec() {
                    return Soul3ParticleOptions.CODEC;
                }
            });
    public static final RegistryObject<ParticleType> SOUL4 =
            PARTICLE_TYPES.register("soul4", () -> new ParticleType<Soul4ParticleOptions>(false, Soul4ParticleOptions.DESERIALIZER) {
                @Override
                public Codec<Soul4ParticleOptions> codec() {
                    return Soul4ParticleOptions.CODEC;
                }
            });

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
