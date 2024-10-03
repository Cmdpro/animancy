package com.cmdpro.animancy.renderers;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.entity.CultistHusk;
import com.cmdpro.databank.model.DatabankEntityModel;
import com.cmdpro.databank.model.DatabankModels;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.phys.Vec3;


public class CultistHuskRenderer extends MobRenderer<CultistHusk, CultistHuskRenderer.Model<CultistHusk>> {
    public static final ModelLayerLocation modelLocation = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "cultist_husk"), "main");
    public CultistHuskRenderer(EntityRendererProvider.Context p_272933_) {
        super(p_272933_, new Model<>(p_272933_.bakeLayer(modelLocation)), 0.5F);
    }
    @Override
    public ResourceLocation getTextureLocation(CultistHusk instance) {
        return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "textures/entity/cultist_husk.png");
    }

    public static class Model<T extends CultistHusk> extends HierarchicalModel<T> {
        public static AnimationDefinition idle;
        public static AnimationDefinition walk;
        public static final AnimationState animState = new AnimationState();
        private final ModelPart root;
        private final ModelPart head;

        public Model(ModelPart pRoot) {
            this.root = pRoot.getChild("root");
            this.head = root.getChild("body").getChild("head");
        }
        public static DatabankEntityModel model;
        public static DatabankEntityModel getModel() {
            if (model == null) {
                model = DatabankModels.models.get(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "cultist_husk"));
                idle = model.animations.get("idle").createAnimationDefinition();
                walk = model.animations.get("walk").createAnimationDefinition();
            }
            return model;
        }

        public static LayerDefinition createLayer() {
            return getModel().createLayerDefinition();
        }
        public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            this.head.xRot = pHeadPitch * (float) (Math.PI / 180.0);
            this.head.yRot = pNetHeadYaw * (float) (Math.PI / 180.0);
            animState.startIfStopped(pEntity.tickCount);
            boolean isMoving;
            isMoving = (pLimbSwingAmount <= -0.015f || pLimbSwingAmount >= 0.015f);
            if (isMoving) {
                this.animate(animState, walk, pAgeInTicks, 1.0f);
            } else {
                this.animate(animState, idle, pAgeInTicks, 1.0f);
            }
        }

        @Override
        public ModelPart root() {
            return root;
        }
    }
}
