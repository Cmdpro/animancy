package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.commands.SpiritmanyCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Spiritmancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeModEvents {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        SpiritmanyCommands.register(event.getDispatcher());
    }
}