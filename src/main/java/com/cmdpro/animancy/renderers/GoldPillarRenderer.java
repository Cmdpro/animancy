package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;

public class GoldPillarRenderer implements BlockEntityRenderer<GoldPillarBlockEntity> {
    public GoldPillarRenderer(BlockEntityRendererProvider.Context context) {
    }
    @Override
    public void render(GoldPillarBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if (pBlockEntity.item != null) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.5D, 1.5D, 0.5D);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getLevel().getLevelData().getGameTime() % 360));
            pPoseStack.scale(0.75F, 0.75F, 0.75F);
            Minecraft.getInstance().getItemRenderer().renderStatic(pBlockEntity.item, ItemDisplayContext.GUI, pPackedLight, pPackedOverlay, pPoseStack, pBuffer, pBlockEntity.getLevel(), 0);
            pPoseStack.popPose();
        }
    }
}
