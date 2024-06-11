package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.api.SoulTankItem;
import com.cmdpro.spiritmancy.init.*;
import com.cmdpro.spiritmancy.integration.PageSoulAltar;
import com.cmdpro.spiritmancy.particle.Soul2Particle;
import com.cmdpro.spiritmancy.particle.Soul3Particle;
import com.cmdpro.spiritmancy.particle.Soul4Particle;
import com.cmdpro.spiritmancy.particle.SoulParticle;
import com.cmdpro.spiritmancy.renderers.*;
import com.cmdpro.spiritmancy.screen.SoulAltarScreen;
import com.cmdpro.spiritmancy.soultypes.SoulType;
import com.cmdpro.spiritmancy.soultypes.SoulTypeManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vazkii.patchouli.client.book.ClientBookRegistry;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Spiritmancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityInit.SOULALTAR.get(), SoulAltarRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.GOLDPILLAR.get(), GoldPillarRenderer::new);
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(soulTankColor, ItemInit.SOULTANK.get());
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
        EntityRenderers.register(EntityInit.SOULKEEPER.get(), SoulKeeperRenderer::new);
        EntityRenderers.register(EntityInit.SOULRITUALCONTROLLER.get(), SoulRitualControllerRenderer::new);

        event.enqueueWork(() -> {
            ItemProperties.register(ItemInit.SOULTANK.get(), new ResourceLocation(Spiritmancy.MOD_ID, "fill"), (stack, level, entity, seed) -> {
                float fill = SoulTankItem.getFill(stack);
                if (fill <= 0.125f && fill > 0) {
                    return 0.125f;
                }
                return fill;
            });
        });

        MenuScreens.register(MenuInit.SOULALTARMENU.get(), SoulAltarScreen::new);

        ClientBookRegistry.INSTANCE.pageTypes.put(new ResourceLocation(Spiritmancy.MOD_ID, "soulaltar"), PageSoulAltar.class);
    }
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.SOUL.get(),
                SoulParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.SOUL2.get(),
                Soul2Particle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.SOUL3.get(),
                Soul3Particle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.SOUL4.get(),
                Soul4Particle.Provider::new);
    }
}
