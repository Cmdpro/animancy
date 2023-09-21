package com.cmdpro.spiritmancy.networking.packet;

import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerDataSyncS2CPacket {
    private final float souls;

    public PlayerDataSyncS2CPacket(float souls) {
        this.souls = souls;
    }

    public PlayerDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.souls = buf.readFloat();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(souls);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPlayerData.set(souls);
        });
        return true;
    }
}