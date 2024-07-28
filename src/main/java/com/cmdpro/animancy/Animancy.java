package com.cmdpro.animancy;

import com.cmdpro.animancy.config.AnimancyConfig;
import com.cmdpro.animancy.registry.*;
import com.cmdpro.animancy.integration.PatchouliMultiblocks;
import com.cmdpro.animancy.networking.ModMessages;
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
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("animancy")
@Mod.EventBusSubscriber(modid = Animancy.MOD_ID)
public class Animancy
{
    public static final ResourceKey<DamageType> magicProjectile = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Animancy.MOD_ID, "magic_projectile"));
    public static ResourceKey<DamageType> soulExplosion = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Animancy.MOD_ID, "soul_explosion"));

    public static final String MOD_ID = "animancy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RandomSource random;
    public Animancy()
    {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the loadComplete method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::loadComplete);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, AnimancyConfig.COMMON_SPEC, "animancy.toml");

        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(bus);
        EntityRegistry.ENTITY_TYPES.register(bus);
        MenuRegistry.register(bus);
        RecipeRegistry.register(bus);
        SoundRegistry.register(bus);
        CreativeModeTabRegistry.register(bus);
        ParticleRegistry.register(bus);
        GeckoLib.initialize();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::addCreative);
        random = RandomSource.create();
    }

    public void loadComplete(FMLLoadCompleteEvent event)
    {
        PatchouliMultiblocks.register();
    }
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

        }
        if (event.getTabKey() == CreativeModeTabRegistry.ITEMS.getKey()) {
            event.accept(ItemRegistry.SOULMETALDAGGER);
            event.accept(ItemRegistry.SOULMETAL);
            event.accept(ItemRegistry.SOULCRYSTAL);
            event.accept(ItemRegistry.CRYSTALSOULSMUSICDISC);
            event.accept(ItemRegistry.THESOULSSCREAMMUSICDISC);
            event.accept(ItemRegistry.THESOULSREVENGEMUSICDISC);
            event.accept(ItemRegistry.ANIMAGITE_INGOT);
            event.accept(ItemRegistry.ANIMAGITE_DAGGER);
            event.accept(ItemRegistry.ANIMAGITE_SWORD);
            event.accept(ItemRegistry.SOULTANK);
            event.accept(ItemRegistry.STRIDERBOOTS);
            event.accept(ItemRegistry.SOUL_STICK);
            event.accept(ItemRegistry.SOULSPIN_STAFF);
        }
        if (event.getTabKey() == CreativeModeTabRegistry.BLOCKS.getKey()) {
            event.accept(ItemRegistry.SOULALTARITEM);
            event.accept(BlockRegistry.GOLDPILLAR);
            event.accept(BlockRegistry.ECHOSOIL);
        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        ModMessages.register();
        event.enqueueWork(CriteriaTriggerRegistry::register);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // Some example code to dispatch IMC to another mod
        InterModComms.sendTo("animancy", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
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
