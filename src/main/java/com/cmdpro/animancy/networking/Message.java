package com.cmdpro.animancy.networking;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public interface Message extends CustomPacketPayload {
    default void handleClient(Minecraft minecraft, Player player) {

    }
    default void handleServer(MinecraftServer server, ServerPlayer player) {

    }
}
