package com.cmdpro.animancy.networking.packet;

import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import com.cmdpro.animancy.soultypes.SoulType;
import com.cmdpro.animancy.soultypes.SoulTypeManager;
import com.cmdpro.animancy.soultypes.SoulTypeSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.Map;
import java.util.function.Supplier;

public class StartSoulAltarSoundS2CPacket {
    private final BlockPos pos;

    public StartSoulAltarSoundS2CPacket(BlockPos pos) {
        this.pos = pos;
    }

    public StartSoulAltarSoundS2CPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPacketHandler.handlePacket(this, supplier);
        });
        return true;
    }
    public static class ClientPacketHandler {
        public static void handlePacket(StartSoulAltarSoundS2CPacket msg, Supplier<NetworkEvent.Context> supplier) {
            if (Minecraft.getInstance().level.getExistingBlockEntity(msg.pos) instanceof SoulAltarBlockEntity ent) {
                SoulAltarBlockEntity.ClientSounds.restart(ent.sound);
            }
        }
    }
}