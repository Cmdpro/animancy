package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.item.SoulPointItem;
import com.cmdpro.spiritmancy.item.SpiritTankItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SoulPointItemModel extends GeoModel<SoulPointItem> {
    @Override
    public ResourceLocation getModelResource(SoulPointItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/soulpoint.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SoulPointItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/soulpoint.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SoulPointItem animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/soulpoint.animation.json");
    }
}