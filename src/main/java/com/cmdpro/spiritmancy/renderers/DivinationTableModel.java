package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.block.entity.DivinationTableBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SoulAltarBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DivinationTableModel extends GeoModel<DivinationTableBlockEntity> {
    @Override
    public ResourceLocation getModelResource(DivinationTableBlockEntity object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/divinationtable.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DivinationTableBlockEntity object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/divinationtable.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DivinationTableBlockEntity animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/divinationtable.animation.json");
    }
}