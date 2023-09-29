package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.block.entity.SoulAltarBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SpiritTankBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class SoulAltarModel extends GeoModel<SoulAltarBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SoulAltarBlockEntity object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/soulaltar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoulAltarBlockEntity object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/soulaltar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoulAltarBlockEntity animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/soulaltar.animation.json");
    }
}