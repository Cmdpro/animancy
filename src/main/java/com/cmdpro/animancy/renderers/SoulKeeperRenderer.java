package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.entity.SoulKeeper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class SoulKeeperRenderer extends HumanoidMobRenderer<SoulKeeper, SoulKeeperRenderer.SoulKeeperModel> {
    public SoulKeeperRenderer(EntityRendererProvider.Context context) {
        super(context, new SoulKeeperModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(SoulKeeper instance) {
        return new ResourceLocation(Animancy.MOD_ID, "textures/entity/soul_keeper.png");
    }
    public static class SoulKeeperModel extends HumanoidModel<SoulKeeper> {
        public SoulKeeperModel(ModelPart pRoot) {
            super(pRoot);
        }
    }
}
