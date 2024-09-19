package com.cmdpro.animancy;

import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.api.Upgrade;
import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import com.cmdpro.animancy.config.AnimancyConfig;
import com.cmdpro.animancy.registry.*;
import com.cmdpro.animancy.integration.PatchouliMultiblocks;
import com.cmdpro.animancy.networking.ModMessages;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;


import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("animancy")
@EventBusSubscriber(modid = Animancy.MOD_ID)
public class Animancy
{
    public static final ResourceKey<DamageType> magicProjectile = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "magic_projectile"));
    public static ResourceKey<DamageType> soulExplosion = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_explosion"));

    public static final String MOD_ID = "animancy";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static RandomSource random;
    public Animancy(IEventBus bus)
    {
        // Register the setup method for modloading
        bus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        bus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        bus.addListener(this::processIMC);
        // Register the loadComplete method for modloading
        bus.addListener(this::loadComplete);

        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        modLoadingContext.getActiveContainer().registerConfig(ModConfig.Type.COMMON, AnimancyConfig.COMMON_SPEC, "animancy.toml");

        ItemRegistry.ITEMS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        BlockEntityRegistry.BLOCK_ENTITIES.register(bus);
        EntityRegistry.ENTITY_TYPES.register(bus);
        MenuRegistry.MENUS.register(bus);
        RecipeRegistry.RECIPE_TYPES.register(bus);
        RecipeRegistry.RECIPES.register(bus);
        SoundRegistry.SOUND_EVENTS.register(bus);
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(bus);
        ParticleRegistry.PARTICLE_TYPES.register(bus);
        CriteriaTriggerRegistry.TRIGGERS.register(bus);
        ArmorMaterialRegistry.ARMOR_MATERIALS.register(bus);
        random = RandomSource.create();
        Upgrade.addDefaultUpgradeChecks();
    }

    public void loadComplete(FMLLoadCompleteEvent event)
    {
        PatchouliMultiblocks.register();
    }
    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {

        }
        if (event.getTabKey() == CreativeModeTabRegistry.getKey(CreativeModeTabRegistry.ITEMS.get())) {
            event.accept(ItemRegistry.SOULMETALDAGGER.get());
            event.accept(ItemRegistry.SOULMETAL.get());
            event.accept(ItemRegistry.SOULCRYSTAL.get());
            event.accept(ItemRegistry.CRYSTALSOULSMUSICDISC.get());
            event.accept(ItemRegistry.THESOULSSCREAMMUSICDISC.get());
            event.accept(ItemRegistry.THESOULSREVENGEMUSICDISC.get());
            event.accept(ItemRegistry.ANIMAGITE_INGOT.get());
            event.accept(ItemRegistry.ANIMAGITE_DAGGER.get());
            event.accept(ItemRegistry.ANIMAGITE_SWORD.get());
            event.accept(ItemRegistry.SOULTANK.get());
            event.accept(ItemRegistry.STRIDERBOOTS.get());
            event.accept(ItemRegistry.SOUL_STICK.get());
            event.accept(ItemRegistry.SOULSPIN_STAFF.get());
            event.accept(ItemRegistry.SPIRIT_BOW.get());
        }
        if (event.getTabKey() == CreativeModeTabRegistry.getKey(CreativeModeTabRegistry.BLOCKS.get())) {
            event.accept(ItemRegistry.SOULALTARITEM.get());
            event.accept(BlockRegistry.GOLDPILLAR.get());
            event.accept(BlockRegistry.ECHOSOIL.get());
            event.accept(BlockRegistry.SOULMETAL_BLOCK.get());
            event.accept(BlockRegistry.ANIMAGITE_BLOCK.get());
            event.accept(BlockRegistry.SPIRITUAL_ANCHOR.get());
        }
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
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
    public static void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }

}
