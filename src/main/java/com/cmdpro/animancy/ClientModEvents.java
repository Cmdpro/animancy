package com.cmdpro.animancy;

import com.cmdpro.animancy.api.SoulTankItem;
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
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.patchouli.client.book.ClientBookRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Animancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        EntityRenderers.register(EntityRegistry.SOULRITUALCONTROLLER.get(), SoulRitualControllerRenderer::new);
        EntityRenderers.register(EntityRegistry.CULTIST_HUSK.get(), CultistHuskRenderer::new);

        event.enqueueWork(() -> {
            ItemProperties.register(ItemRegistry.SOULTANK.get(), new ResourceLocation(Animancy.MOD_ID, "fill"), (stack, level, entity, seed) -> {
                float fill = SoulTankItem.getFill(stack);
                if (fill <= 0.125f && fill > 0) {
                    return 0.125f;
                }
                return fill;
            });
        });

        MenuScreens.register(MenuRegistry.SOULALTARMENU.get(), SoulAltarScreen::new);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(Animancy.MOD_ID, "soul_altar"), PageSoulAltar.class);
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
