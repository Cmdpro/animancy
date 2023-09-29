package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.item.SoulAltarItem;
import com.cmdpro.spiritmancy.item.SoulPointItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SoulAltarItemModel extends GeoModel<SoulAltarItem> {
    @Override
    public ResourceLocation getModelResource(SoulAltarItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/soulaltar.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoulAltarItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/soulaltar.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoulAltarItem animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/soulaltar.animation.json");
    }
}