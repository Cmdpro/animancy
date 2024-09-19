package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;


public class SoulAltarRenderer extends GeoBlockRenderer<SoulAltarBlockEntity> {

    public SoulAltarRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(new SoulAltarModel());
    }

    @Override
    public RenderType getRenderType(SoulAltarBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }

    @Override
    public void postRender(PoseStack poseStack, SoulAltarBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        super.postRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
        poseStack.pushPose();
        poseStack.translate(0D, 1.5D, 0D);
        poseStack.mulPose(Axis.YP.rotationDegrees(animatable.getLevel().getLevelData().getGameTime() % 360));
        poseStack.scale(0.75F, 0.75F, 0.75F);
        Minecraft.getInstance().getItemRenderer().renderStatic(animatable.item, ItemDisplayContext.GUI, packedLight, packedOverlay, poseStack, bufferSource, animatable.getLevel(), 0);
        poseStack.popPose();
    }
    public static class SoulAltarModel extends GeoModel<SoulAltarBlockEntity> {
        @Override
        public ResourceLocation getModelResource(SoulAltarBlockEntity object) {
            return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "geo/soul_altar.geo.json");
        }

        @Override
        public ResourceLocation getTextureResource(SoulAltarBlockEntity object) {
            return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "textures/block/soul_altar.png");
        }

        @Override
        public ResourceLocation getAnimationResource(SoulAltarBlockEntity animatable) {
            return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "animations/soul_altar.animation.json");
        }
    }
}