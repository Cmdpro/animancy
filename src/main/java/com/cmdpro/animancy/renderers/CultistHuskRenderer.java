package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.entity.CultistHusk;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class CultistHuskRenderer extends GeoEntityRenderer<CultistHusk> {
    public CultistHuskRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new Model());
        this.shadowRadius = 0.5f;
    }

    @Override
    public float getMotionAnimThreshold(CultistHusk animatable) {
        return 0.005f;
    }

    @Override
    public ResourceLocation getTextureLocation(CultistHusk instance) {
        return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "textures/entity/cultist_husk.png");
    }
    public static class Model extends GeoModel<CultistHusk> {
        @Override
        public ResourceLocation getModelResource(CultistHusk object) {
            return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "geo/cultist_husk.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(CultistHusk object) {
            return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "textures/entity/cultist_husk.png");
        }

        @Override
        public ResourceLocation getAnimationResource(CultistHusk animatable) {
            return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "animations/cultist_husk.animation.json");
        }
    }
}
