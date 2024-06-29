package com.cmdpro.animancy.particle;

import com.cmdpro.animancy.registry.ParticleRegistry;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public class Soul4ParticleOptions implements ParticleOptions {
    public ResourceLocation soulType;
    public Soul4ParticleOptions(String soulType) {
        this.soulType = ResourceLocation.tryParse(soulType);
    }
    @Override
    public ParticleType<?> getType() {
        return ParticleRegistry.SOUL4.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeResourceLocation(soulType);
    }
    public static final Codec<Soul4ParticleOptions> CODEC = RecordCodecBuilder.create((p_253370_) -> {
        return p_253370_.group(Codec.STRING.fieldOf("soulType").forGetter((p_253371_) -> {
            return p_253371_.soulType.toString();
        })).apply(p_253370_, Soul4ParticleOptions::new);
    });
    public static final Deserializer<Soul4ParticleOptions> DESERIALIZER = new Deserializer<Soul4ParticleOptions>() {
        @Override
        public Soul4ParticleOptions fromCommand(ParticleType<Soul4ParticleOptions> options, StringReader reader) throws CommandSyntaxException {
            String type = reader.readString();
            return new Soul4ParticleOptions(type);
        }
        @Override
        public Soul4ParticleOptions fromNetwork(ParticleType<Soul4ParticleOptions> options, FriendlyByteBuf buf) {
            String type = buf.readResourceLocation().toString();
            return new Soul4ParticleOptions(type);
        }
    };

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %s", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.soulType);
    }
}