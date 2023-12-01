package com.cmdpro.spiritmancy.commands;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import com.cmdpro.spiritmancy.moddata.PlayerModData;
import com.cmdpro.spiritmancy.moddata.PlayerModDataProvider;
import com.klikli_dev.modonomicon.book.Book;
import com.klikli_dev.modonomicon.bookstate.BookUnlockStateManager;
import com.klikli_dev.modonomicon.data.BookDataManager;
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
        dispatcher.register(Commands.literal(Spiritmancy.MOD_ID)
                .requires(source -> source.hasPermission(4))
                .then(Commands.literal("setsouls")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes((command) -> {
                                    return setsouls(command);
                                })
                        )
                )
                .then(Commands.literal("spawnsoulkeeper")
                    .executes((command) -> {
                        return spawnsoulkeeper(command);
                    })
                )
                .then(Commands.literal("setknowledge")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes((command) -> {
                                    return setknowledge(command);
                                })
                        )
                )
                .then(Commands.literal("setancientknowledge")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes((command) -> {
                                    return setancientknowledge(command);
                                })
                        )
                )
                .then(Commands.literal("resetlearned")
                        .executes((command) -> {
                            return resetlearned(command);
                        })
                )
        );
    }
    private static int resetlearned(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof Player) {
            Player player = (Player) command.getSource().getEntity();
            player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                data.getUnlocked().clear();
                BookUnlockStateManager.get().resetFor((ServerPlayer)player, BookDataManager.get().getBook(new ResourceLocation("spiritmancy", "spiritmancyguide")));
                BookUnlockStateManager.get().updateAndSyncFor((ServerPlayer)player);
            });
        }
        return Command.SINGLE_SUCCESS;
    }
    private static int spawnsoulkeeper(CommandContext<CommandSourceStack> command) {
        SpiritmancyUtil.spawnSoulKeeper(command.getSource().getPosition(), command.getSource().getLevel());
        return Command.SINGLE_SUCCESS;
    }
    private static int setsouls(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof Player) {
            Player player = (Player) command.getSource().getEntity();
            player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                float souls = command.getArgument("amount", int.class);
                if (PlayerModData.getMaxSouls(player) >= souls) {
                    data.setSouls(souls);
                } else {
                    data.setSouls(PlayerModData.getMaxSouls(player));
                }
            });
        }
        return Command.SINGLE_SUCCESS;
    }
    private static int setknowledge(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof Player) {
            Player player = (Player) command.getSource().getEntity();
            player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                int knowledge = command.getArgument("amount", int.class);
                data.setKnowledge(knowledge);
            });
        }
        return Command.SINGLE_SUCCESS;
    }
    private static int setancientknowledge(CommandContext<CommandSourceStack> command){
        if(command.getSource().getEntity() instanceof Player) {
            Player player = (Player) command.getSource().getEntity();
            player.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent(data -> {
                int knowledge = command.getArgument("amount", int.class);
                data.setAncientKnowledge(knowledge);
            });
        }
        return Command.SINGLE_SUCCESS;
    }
}
