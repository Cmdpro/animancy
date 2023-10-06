package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.init.*;
import com.cmdpro.spiritmancy.integration.BookAltarRecipePage;
import com.cmdpro.spiritmancy.integration.SpiritmancyModonomiconConstants;
import com.cmdpro.spiritmancy.networking.ModMessages;
import com.klikli_dev.modonomicon.data.LoaderRegistry;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("spiritmancy")
@Mod.EventBusSubscriber(modid = Spiritmancy.MOD_ID)
public class Spiritmancy
{
    public static ResourceKey<DamageType> soulExplosion = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Spiritmancy.MOD_ID, "soulexplosion"));

    public static final String MOD_ID = "spiritmancy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RandomSource random;

    public Spiritmancy()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        EntityInit.ENTITY_TYPES.register(bus);
        MenuInit.register(bus);
        RecipeInit.register(bus);
        SoundInit.register(bus);
        CreativeModeTabInit.register(bus);
        ParticleInit.register(bus);
        SoulcasterEffectInit.SOULCASTER_EFFECTS.register(bus);
        GeckoLib.initialize();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        random = RandomSource.create();
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

        }
        if (event.getTabKey() == CreativeModeTabInit.ITEMS.getKey()) {
            event.accept(ItemInit.SOULMETALDAGGER);
            event.accept(ItemInit.SOULMETAL);
            event.accept(ItemInit.SOULLINKER);
            event.accept(ItemInit.SOULCRYSTAL);
            event.accept(ItemInit.SOULFOCUS);
            event.accept(ItemInit.CRYSTALSOULSMUSICDISC);
        }
        if (event.getTabKey() == CreativeModeTabInit.BLOCKS.getKey()) {
            event.accept(ItemInit.SPIRITTANK_ITEM);
            event.accept(ItemInit.SOULPOINT_ITEM);
            event.accept(ItemInit.SOULALTAR_ITEM);
            event.accept(BlockInit.SOULSHAPER.get());
        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        ModMessages.register();
        event.enqueueWork(ModCriteriaTriggers::register);
        LoaderRegistry.registerPageLoader(SpiritmancyModonomiconConstants.Page.ALTAR_RECIPE, BookAltarRecipePage::fromJson, BookAltarRecipePage::fromNetwork);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("spiritmancy", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // Some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.messageSupplier().get()).
                collect(Collectors.toList()));
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }


}
