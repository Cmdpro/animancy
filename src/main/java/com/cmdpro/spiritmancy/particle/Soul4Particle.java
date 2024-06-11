package com.cmdpro.spiritmancy.particle;

import com.cmdpro.spiritmancy.init.ParticleInit;
import com.cmdpro.spiritmancy.soultypes.SoulType;
import com.cmdpro.spiritmancy.soultypes.SoulTypeManager;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.RandomUtils;

@OnlyIn(Dist.CLIENT)
public class Soul4Particle extends TextureSheetParticle {
    public float startQuadSize;
    public Soul4ParticleOptions options;
    protected Soul4Particle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                            SpriteSet spriteSet, double xd, double yd, double zd, Soul4ParticleOptions options) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.friction = 0.8F;
        this.xd = xd;
        this.yd = yd;
        this.zd = zd;
        this.quadSize *= 0.85F;
        startQuadSize = this.quadSize;
        this.lifetime = 20;
        this.setSpriteFromAge(spriteSet);

        this.rCol = (float)115/255f;
        this.gCol = (float)185/255f;
        this.bCol = (float)255/255f;
        SoulType type = SoulTypeManager.types.get(options.soulType);
        if (type != null) {
            this.rCol = (float)type.color.getRed()/255f;
            this.gCol = (float)type.color.getGreen()/255f;
            this.bCol = (float)type.color.getBlue()/255f;
        }
        this.alpha = 1f;
        this.hasPhysics = true;
        this.options = options;
    }
    @Override
    public void tick() {
        super.tick();
        fadeOut();
    }

    private void fadeOut() {
        this.quadSize = (-(1/(float)lifetime) * age + 1)*startQuadSize;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SoulParticle.SOULRENDER;
    }
    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<Soul4ParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(Soul4ParticleOptions particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new Soul4Particle(level, x, y, z, this.sprites, dx, dy, dz, particleType);
        }
    }
}
