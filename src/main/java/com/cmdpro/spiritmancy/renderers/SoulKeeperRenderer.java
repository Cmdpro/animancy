package com.cmdpro.spiritmancy.renderers;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.entity.SoulKeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class SoulKeeperRenderer extends HumanoidMobRenderer<SoulKeeper, SoulKeeperModel> {
    public SoulKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulKeeperModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SoulKeeper instance) {
        return new ResourceLocation(Spiritmancy.MOD_ID, "textures/entity/soulkeeper.png");
    }
}
