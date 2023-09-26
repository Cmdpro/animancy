package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.block.entity.SoulPointBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SpiritTankBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.loading.json.raw.Bone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SoulPointModel extends GeoModel<SoulPointBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SoulPointBlockEntity object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/soulpoint.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoulPointBlockEntity object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/soulpoint.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoulPointBlockEntity animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/soulpoint.animation.json");
    }
}