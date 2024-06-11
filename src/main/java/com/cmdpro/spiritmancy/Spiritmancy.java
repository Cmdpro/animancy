package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.config.SpiritmancyConfig;
import com.cmdpro.spiritmancy.init.*;
import com.cmdpro.spiritmancy.integration.PageSoulAltar;
import com.cmdpro.spiritmancy.integration.PatchouliMultiblocks;
import com.cmdpro.spiritmancy.networking.ModMessages;
import com.google.common.collect.ImmutableList;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.client.book.ClientBookRegistry;


import javax.sound.midi.Patch;
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
    public static ImmutableList<EntityType<? extends LivingEntity>> soulCrystalEntities = ImmutableList.of();
    public Spiritmancy()
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

        modLoadingContext.registerConfig(ModConfig.Type.COMMON, SpiritmancyConfig.COMMON_SPEC, "spiritmancy.toml");

        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        EntityInit.ENTITY_TYPES.register(bus);
        MenuInit.register(bus);
        RecipeInit.register(bus);
        SoundInit.register(bus);
        CreativeModeTabInit.register(bus);
        ParticleInit.register(bus);
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
        if (event.getTabKey() == CreativeModeTabInit.ITEMS.getKey()) {
            event.accept(ItemInit.SOULMETALDAGGER);
            event.accept(ItemInit.SOULMETAL);
            event.accept(ItemInit.SOULCRYSTAL);
            event.accept(ItemInit.CRYSTALSOULSMUSICDISC);
            event.accept(ItemInit.THESOULSSCREAMMUSICDISC);
            event.accept(ItemInit.THESOULSREVENGEMUSICDISC);
            event.accept(ItemInit.PURGATORYINGOT);
            event.accept(ItemInit.PURGATORYDAGGER);
            event.accept(ItemInit.PURGATORYSWORD);
            event.accept(ItemInit.SOULTANK);
        }
        if (event.getTabKey() == CreativeModeTabInit.BLOCKS.getKey()) {
            event.accept(ItemInit.SOULALTARITEM);
            event.accept(BlockInit.GOLDPILLAR);
            event.accept(BlockInit.ECHOSOIL);
        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        ModMessages.register();
        event.enqueueWork(ModCriteriaTriggers::register);
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
