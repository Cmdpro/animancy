package com.cmdpro.animancy.particle;

import com.cmdpro.animancy.soultypes.SoulType;
import com.cmdpro.animancy.soultypes.SoulTypeManager;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.RandomUtils;

public class Soul3Particle extends TextureSheetParticle {
    public float startQuadSize;
    public Soul3ParticleOptions options;
    protected Soul3Particle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                            SpriteSet spriteSet, double xd, double yd, double zd, Soul3ParticleOptions options) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        this.friction = 0.8F;
        this.xd = xd+RandomUtils.nextFloat(0f, 0.5f)-0.25f;
        this.yd = yd+0.75;
        this.zd = zd+RandomUtils.nextFloat(0f, 0.5f)-0.25f;
        this.quadSize *= 1.25F;
        startQuadSize = this.quadSize;
        this.lifetime = 2;
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
        this.spd = 0.25f;
        ticks = 0;
    }
    public float spd;
    public int ticks;
    @Override
    public void tick() {
        super.tick();
        ticks++;
        if (ticks >= 10) {
            hasPhysics = false;
            Vec3 pos = getPos();
            Vec3 plrPos = level.getPlayerByUUID(options.player).position();
            pos = pos.lerp(plrPos, spd);
            setPos(pos.x, pos.y, pos.z);
            spd += 0.05f;
            if (pos.distanceTo(plrPos) > 0.1f) {
                age = 0;
            }
        } else {
            age = 0;
            hasPhysics = true;
            yd -= ticks*0.01f;
        }
        level.addParticle(new Soul4ParticleOptions(options.soulType), x, y, z, 0, 0, 0);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SoulParticle.ADDITIVE;
    }

    public static class Provider implements ParticleProvider<Soul3ParticleOptions> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(Soul3ParticleOptions particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new Soul3Particle(level, x, y, z, this.sprites, dx, dy, dz, particleType);
        }
    }
}
