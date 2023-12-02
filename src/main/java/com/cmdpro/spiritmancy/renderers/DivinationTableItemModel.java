package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.item.DivinationTableItem;
import com.cmdpro.spiritmancy.item.SoulAltarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DivinationTableItemModel extends GeoModel<DivinationTableItem> {
    @Override
    public ResourceLocation getModelResource(DivinationTableItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "geo/divinationtable.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DivinationTableItem object) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/block/divinationtable.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DivinationTableItem animatable) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "animations/divinationtable.animation.json");
    }
}