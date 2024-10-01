package com.cmdpro.animancy;

import com.cmdpro.animancy.api.SoulAltarUpgrade;
import com.cmdpro.animancy.config.AnimancyConfig;
import com.cmdpro.animancy.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod("animancy")
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Animancy.MOD_ID)
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
        DataComponentRegistry.DATA_COMPONENTS.register(bus);
        AttachmentTypeRegistry.ATTACHMENT_TYPES.register(bus);
        random = RandomSource.create();
        SoulAltarUpgrade.addDefaultUpgradeChecks();
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

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
            event.accept(ItemRegistry.SOULBOUND_BELL.get());
        }
        if (event.getTabKey() == CreativeModeTabRegistry.getKey(CreativeModeTabRegistry.BLOCKS.get())) {
            event.accept(BlockRegistry.SOULALTAR.get());
            event.accept(BlockRegistry.GOLDPILLAR.get());
            event.accept(BlockRegistry.ECHOSOIL.get());
            event.accept(BlockRegistry.SOULMETAL_BLOCK.get());
            event.accept(BlockRegistry.ANIMAGITE_BLOCK.get());
            event.accept(BlockRegistry.SPIRITUAL_ANCHOR.get());
        }
    }

}
