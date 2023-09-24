package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.item.SpiritTankItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SpiritTankItemModel extends GeoModel<SpiritTankItem> {
    @Override
    public ResourceLocation getModelResource(SpiritTankItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/spirittank.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpiritTankItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/spirittank.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpiritTankItem animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/spirittank.animation.json");
    }
}