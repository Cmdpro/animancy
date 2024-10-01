package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;


public class SoulAltarRenderer implements BlockEntityRenderer<SoulAltarBlockEntity> {
    public SoulAltarRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(SoulAltarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        pPoseStack.pushPose();
        pPoseStack.translate(0.5D, 1.5D, 0.5D);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getLevel().getLevelData().getGameTime() % 360));
        pPoseStack.scale(0.75F, 0.75F, 0.75F);
        Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.item, ItemDisplayContext.GUI, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
        pPoseStack.popPose();
    }
}