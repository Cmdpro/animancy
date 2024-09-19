package com.cmdpro.animancy;

import com.cmdpro.animancy.commands.SpiritmanyCommands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
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
}
