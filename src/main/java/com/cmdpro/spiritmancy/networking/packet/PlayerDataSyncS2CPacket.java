package com.cmdpro.spiritmancy.networking.packet;

import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerDataSyncS2CPacket {
    private final float souls;
    private final int knowledge;

    public PlayerDataSyncS2CPacket(float souls, int knowledge) {
        this.souls = souls;
        this.knowledge = knowledge;
    }

    public PlayerDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.souls = buf.readFloat();
        this.knowledge = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(souls);
        buf.writeInt(knowledge);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPlayerData.set(souls, knowledge);
        });
        return true;
    }
}