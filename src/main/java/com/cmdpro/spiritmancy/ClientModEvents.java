package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.client.ModHud;
import com.cmdpro.spiritmancy.api.SpiritmancyRegistration;
import com.cmdpro.spiritmancy.init.*;
import com.cmdpro.spiritmancy.integration.BookAltarRecipePage;
import com.cmdpro.spiritmancy.integration.BookAltarRecipePageRenderer;
import com.cmdpro.spiritmancy.integration.SpiritmancyModonomiconConstants;
import com.cmdpro.spiritmancy.particle.Soul2Particle;
import com.cmdpro.spiritmancy.particle.SoulParticle;
import com.cmdpro.spiritmancy.renderers.*;
import com.cmdpro.spiritmancy.screen.SoulShaperScreen;
import com.cmdpro.spiritmancy.screen.SoulcastersTableScreen;
import com.klikli_dev.modonomicon.client.render.page.PageRendererRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Spiritmancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {
    public static final IGuiOverlay HUD = ModHud::drawHUD;
    @SubscribeEvent
    public static void renderHUD(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("mod_hud", HUD);
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.register(EntityInit.SPELLPROJECTILE.get(), BillboardProjectileRenderer::new);

        event.registerBlockEntityRenderer(BlockEntityInit.SPIRITTANK.get(), SpiritTankRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.SOULPOINT.get(), SoulPointRenderer::new);
        event.registerBlockEntityRenderer(BlockEntityInit.SOULALTAR.get(), SoulAltarRenderer::new);
    }
    @SubscribeEvent
    public static void doSetup(FMLClientSetupEvent event) {
        MenuScreens.register(MenuInit.SOULSHAPER_MENU.get(), SoulShaperScreen::new);
        MenuScreens.register(MenuInit.SOULCASTERSTABLE_MENU.get(), SoulcastersTableScreen::new);
        PageRendererRegistry.registerPageRenderer(SpiritmancyModonomiconConstants.Page.ALTAR_RECIPE, p -> new BookAltarRecipePageRenderer((BookAltarRecipePage) p));
    }
    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.SOUL.get(),
                SoulParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.SOUL2.get(),
                Soul2Particle.Provider::new);
    }
}
