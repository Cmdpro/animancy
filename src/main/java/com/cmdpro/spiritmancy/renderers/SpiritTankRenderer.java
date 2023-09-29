package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.ClientEvents;
import com.cmdpro.spiritmancy.block.entity.SpiritTankBlockEntity;
import com.cmdpro.spiritmancy.init.ItemInit;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;




public class SpiritTankRenderer extends GeoBlockRenderer<SpiritTankBlockEntity> {

    public SpiritTankRenderer(BlockEntityRendererProvider.Context rendererProvider) {
        super(new SpiritTankModel());
    }

    @Override
    public RenderType getRenderType(SpiritTankBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}