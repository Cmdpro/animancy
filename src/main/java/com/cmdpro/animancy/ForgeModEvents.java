package com.cmdpro.animancy;

import com.cmdpro.animancy.commands.SpiritmanyCommands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Animancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeModEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        SpiritmanyCommands.register(event.getDispatcher());
    }
    @SubscribeEvent
    public static void onLootTablesLoaded(LootTableLoadEvent event) {

        if (event.getName().toString().equals("minecraft:chests/simple_dungeon")) {
            event.getTable().addPool(
                    LootPool.lootPool().add(LootTableReference.lootTableReference(new ResourceLocation(Animancy.MOD_ID, "inject/simple_dungeon")).setWeight(1))
                            .build()
            );
        }
    }
}
