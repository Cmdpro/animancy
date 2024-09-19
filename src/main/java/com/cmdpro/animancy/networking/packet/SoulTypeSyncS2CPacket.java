package com.cmdpro.animancy.networking.packet;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.networking.Message;
import com.cmdpro.animancy.soultypes.SoulType;
import com.cmdpro.animancy.soultypes.SoulTypeManager;
import com.cmdpro.animancy.soultypes.SoulTypeSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Map;

public record SoulTypeSyncS2CPacket(Map<ResourceLocation, SoulType> types) implements Message {

    public static void write(RegistryFriendlyByteBuf buf, SoulTypeSyncS2CPacket obj) {
        buf.writeMap(obj.types, ResourceLocation.STREAM_CODEC, ((pBuffer, pValue) -> SoulTypeSerializer.toNetwork((RegistryFriendlyByteBuf) pBuffer, pValue)));
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static final CustomPacketPayload.Type<SoulTypeSyncS2CPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_type_sync"));

    public static SoulTypeSyncS2CPacket read(RegistryFriendlyByteBuf buf) {
        Map<ResourceLocation, SoulType> types = buf.readMap(ResourceLocation.STREAM_CODEC, (pBuffer) -> SoulTypeSerializer.fromNetwork((RegistryFriendlyByteBuf) pBuffer));
        return new SoulTypeSyncS2CPacket(types);
    }

    @Override
    public void handleClient(Minecraft minecraft, Player player) {
        SoulTypeManager.types.clear();
        SoulTypeManager.types.putAll(types);
    }
}