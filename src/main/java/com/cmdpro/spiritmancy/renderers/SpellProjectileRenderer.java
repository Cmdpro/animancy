package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.entity.BillboardProjectile;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

public class SpellProjectileRenderer extends BillboardProjectileRenderer {

    public SpellProjectileRenderer(EntityRendererProvider.Context p_173962_) {
        super(p_173962_);
    }

    public void render(BillboardProjectile proj, float p_114081_, float p_114082_, PoseStack stack, MultiBufferSource p_114084_, int p_114085_) {
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.ONE.value);
        super.render(proj, p_114081_, p_114082_, stack, p_114084_, p_114085_);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA.value, GlStateManager.DestFactor.DST_ALPHA.value);
    }
}