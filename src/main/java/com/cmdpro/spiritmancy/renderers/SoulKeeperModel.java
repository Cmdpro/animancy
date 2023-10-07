package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.entity.SoulKeeper;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;


public class SoulKeeperModel extends DefaultedEntityGeoModel<SoulKeeper> {
    public SoulKeeperModel() {
        super(new ResourceLocation(Spiritmancy.MOD_ID, "soulkeeper"), true);
    }
    @Override
    public ResourceLocation getModelResource(SoulKeeper object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/soulkeeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoulKeeper object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/entity/soulkeeper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoulKeeper animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/soulkeeper.animation.json");
    }
}
