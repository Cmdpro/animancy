package com.cmdpro.spiritmancy.networking.packet;

import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerDataSyncS2CPacket {
    private final float souls;
    private final int knowledge;
    private final int ancientknowledge;

    public PlayerDataSyncS2CPacket(float souls, int knowledge, int ancientknowledge) {
        this.souls = souls;
        this.knowledge = knowledge;
        this.ancientknowledge = ancientknowledge;
    }

    public PlayerDataSyncS2CPacket(FriendlyByteBuf buf) {
        this.souls = buf.readFloat();
        this.knowledge = buf.readInt();
        this.ancientknowledge = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(souls);
        buf.writeInt(knowledge);
        buf.writeInt(ancientknowledge);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientPlayerData.set(souls, knowledge, ancientknowledge);
        });
        return true;
    }
}