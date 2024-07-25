package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.item.SoulAltarItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SoulAltarItemRenderer extends GeoItemRenderer<SoulAltarItem> {
    public SoulAltarItemRenderer() {
        super(new SoulAltarItemModel());
    }
    public static class SoulAltarItemModel extends GeoModel<SoulAltarItem> {
        @Override
        public ResourceLocation getModelResource(SoulAltarItem object) {
            return new ResourceLocation(Animancy.MOD_ID, "geo/soul_altar.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(SoulAltarItem object) {
            return new ResourceLocation(Animancy.MOD_ID, "textures/block/soul_altar.png");
        }

        @Override
        public ResourceLocation getAnimationResource(SoulAltarItem animatable) {
            return new ResourceLocation(Animancy.MOD_ID, "animations/soul_altar.animation.json");
        }
    }
}