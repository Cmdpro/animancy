package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.entity.SoulKeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class SoulKeeperRenderer extends GeoEntityRenderer<SoulKeeper> {
    public SoulKeeperRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SoulKeeperModel());
        this.shadowRadius = 0.5f;
    }
    @Override
    public ResourceLocation getTextureLocation(SoulKeeper instance) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/entity/soulkeeper.png");
    }

    @Override
    public RenderType getRenderType(SoulKeeper animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
