package com.cmdpro.animancy;

import com.cmdpro.animancy.commands.SpiritmanyCommands;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.fml.common.Mod;

@EventBusSubscriber(modid = Animancy.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ForgeModEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        SpiritmanyCommands.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event) {

        if (event.getName().toString().equals("minecraft:chests/simple_dungeon")) {
            event.getTable().addPool(
                    LootPool.lootPool().add(NestedLootTable.lootTableReference(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "inject/simple_dungeon"))).setWeight(1))
                            .build()
            );
        }
    }
}
