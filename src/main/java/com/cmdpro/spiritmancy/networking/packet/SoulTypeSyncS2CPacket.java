package com.cmdpro.spiritmancy.networking.packet;

import com.cmdpro.spiritmancy.soultypes.SoulType;
import com.cmdpro.spiritmancy.soultypes.SoulTypeManager;
import com.cmdpro.spiritmancy.soultypes.SoulTypeSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class SoulTypeSyncS2CPacket {
    private final Map<ResourceLocation, SoulType> types;

    public SoulTypeSyncS2CPacket(Map<ResourceLocation, SoulType> blocks) {
        this.types = blocks;
    }

    public SoulTypeSyncS2CPacket(FriendlyByteBuf buf) {
        this.types = buf.readMap(FriendlyByteBuf::readResourceLocation, SoulTypeSerializer::fromNetwork);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeMap(types, FriendlyByteBuf::writeResourceLocation, SoulTypeSerializer::toNetwork);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandler.handlePacket(this, supplier);
        });
        return true;
    }
    public static class ClientPacketHandler {
        public static void handlePacket(SoulTypeSyncS2CPacket msg, Supplier<NetworkEvent.Context> supplier) {
            SoulTypeManager.types = msg.types;
        }
    }
}