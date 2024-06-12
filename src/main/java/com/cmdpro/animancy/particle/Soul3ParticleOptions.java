package com.cmdpro.animancy.particle;

import com.cmdpro.animancy.init.ParticleInit;
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
import java.util.UUID;

public class Soul3ParticleOptions implements ParticleOptions {
    public UUID player;
    public ResourceLocation soulType;
    public Soul3ParticleOptions(String player, String soulType) {
        this.player = UUID.fromString(player);
        this.soulType = ResourceLocation.tryParse(soulType);
    }
    @Override
    public ParticleType<?> getType() {
        return ParticleInit.SOUL3.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf pBuffer) {
        pBuffer.writeUUID(player);
        pBuffer.writeResourceLocation(soulType);
    }
    public static final Codec<Soul3ParticleOptions> CODEC = RecordCodecBuilder.create((p_253370_) -> {
        return p_253370_.group(Codec.STRING.fieldOf("player").forGetter((p_253371_) -> {
            return p_253371_.player.toString();
        }), Codec.STRING.fieldOf("soulType").forGetter((p_253371_) -> {
            return p_253371_.soulType.toString();
        })).apply(p_253370_, Soul3ParticleOptions::new);
    });
    public static final ParticleOptions.Deserializer<Soul3ParticleOptions> DESERIALIZER = new ParticleOptions.Deserializer<Soul3ParticleOptions>() {
        @Override
        public Soul3ParticleOptions fromCommand(ParticleType<Soul3ParticleOptions> options, StringReader reader) throws CommandSyntaxException {
            String uuid = reader.readString();
            String type = reader.readString();
            return new Soul3ParticleOptions(uuid, type);
        }
        @Override
        public Soul3ParticleOptions fromNetwork(ParticleType<Soul3ParticleOptions> options, FriendlyByteBuf buf) {
            String uuid = buf.readUUID().toString();
            String type = buf.readResourceLocation().toString();
            return new Soul3ParticleOptions(uuid, type);
        }
    };

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %s", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.player, this.soulType);
    }
}