package com.cmdpro.animancy.commands;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.AnimancyUtil;
import com.cmdpro.animancy.moddata.PlayerModData;
import com.cmdpro.animancy.moddata.PlayerModDataProvider;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class SpiritmanyCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal(Animancy.MOD_ID)
                .requires(source -> source.hasPermission(4))
        );
    }
}
