package com.cmdpro.animancy;

import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.entity.SpiritArrow;
import com.cmdpro.animancy.registry.*;
import com.cmdpro.animancy.integration.PageSoulAltar;
import com.cmdpro.animancy.particle.Soul2Particle;
import com.cmdpro.animancy.particle.Soul3Particle;
import com.cmdpro.animancy.particle.Soul4Particle;
import com.cmdpro.animancy.particle.SoulParticle;
import com.cmdpro.animancy.renderers.*;
import com.cmdpro.animancy.screen.SoulAltarScreen;
import com.cmdpro.animancy.soultypes.SoulType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.patchouli.client.book.ClientBookRegistry;

@EventBusSubscriber(value = Dist.CLIENT, modid = Animancy.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegistry.SOULALTAR.get(), SoulAltarRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityRegistry.GOLDPILLAR.get(), GoldPillarRenderer::new);
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(soulTankColor, ItemRegistry.SOULTANK.get());
    }
    public static ItemColor soulTankColor = new ItemColor() {
        @Override
        public int getColor(ItemStack pStack, int pTintIndex) {
            if (pTintIndex == 1) {
                SoulType soulType = SoulTankItem.getFillType(pStack);
                if (soulType != null) {
                    return soulType.color.getRGB();
                }
            }
            return 0xFFFFFF;
        }
    };
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(EntityRegistry.SOULKEEPER.get(), SoulKeeperRenderer::new);
        EntityRenderers.register(EntityRegistry.SOULRITUALCONTROLLER.get(), EmptyEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.CULTIST_HUSK.get(), CultistHuskRenderer::new);
        EntityRenderers.register(EntityRegistry.SOUL_PROJECTILE.get(), EmptyEntityRenderer::new);
        EntityRenderers.register(EntityRegistry.SPIRIT_ARROW.get(), (a) -> new ArrowRenderer<>(a) {
            @Override
            public ResourceLocation getTextureLocation(SpiritArrow pEntity) {
                return ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "textures/entity/spirit_arrow.png");
            }
        });

        event.enqueueWork(() -> {
            ItemProperties.register(ItemRegistry.SOULTANK.get(), ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), (stack, level, entity, seed) -> {
                float fill = SoulTankItem.getFill(stack);
                if (fill <= 0.125f && fill > 0) {
                    return 0.125f;
                }
                return fill;
            });
            ItemProperties.register(ItemRegistry.SPIRIT_BOW.get(), ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
                if (p_174637_ == null) {
                    return 0.0F;
                } else {
                    return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration(p_174637_) - p_174637_.getUseItemRemainingTicks()) / 20.0F;
                }
            });
            ItemProperties.register(ItemRegistry.SPIRIT_BOW.get(), ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> {
                return p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F;
            });
        });

        ClientBookRegistry.INSTANCE.pageTypes.put(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_altar"), PageSoulAltar.class);
    }
    @SubscribeEvent
    public static void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MenuRegistry.SOULALTARMENU.get(), SoulAltarScreen::new);
    }
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SOUL.get(),
                SoulParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SOUL2.get(),
                Soul2Particle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SOUL3.get(),
                Soul3Particle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleRegistry.SOUL4.get(),
                Soul4Particle.Provider::new);
    }
}
