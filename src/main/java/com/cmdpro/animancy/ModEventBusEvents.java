package com.cmdpro.animancy;

import com.cmdpro.animancy.config.AnimancyConfig;
import com.cmdpro.animancy.entity.*;
import com.cmdpro.animancy.integration.PatchouliMultiblocks;
import com.cmdpro.animancy.registry.EntityRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerLifecycleEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber(modid = Animancy.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void entitySpawnRestriction(RegisterSpawnPlacementsEvent event) {

        event.register(EntityRegistry.CULTIST_HUSK.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(EntityRegistry.SOULKEEPER.get(), SoulKeeper.setAttributes());
        event.put(EntityRegistry.CULTIST_HUSK.get(), CultistHusk.setAttributes());
    }
    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if (config.getSpec() == AnimancyConfig.COMMON_SPEC) {
            AnimancyConfig.bake(config);
        }
    }
    @SubscribeEvent
    public static void loadComplete(FMLLoadCompleteEvent event)
    {
        PatchouliMultiblocks.register();
    }
}
