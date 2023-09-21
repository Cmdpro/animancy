package com.cmdpro.spiritmancy.moddata;

import com.cmdpro.spiritmancy.networking.ModMessages;
import com.cmdpro.spiritmancy.networking.packet.PlayerDataSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerModData {
    public PlayerModData() {
        souls = 0;
    }
    private float souls;
    public static final float MAX_SOULS = 50;
    public float getSouls() {
        return souls;
    }
    public void setSouls(float amount) {
        this.souls = amount;
    }

    public void updateData(ServerPlayer player) {
        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(getSouls()), (player));
    }
    public void updateData(Player player) {
        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(getSouls()), ((ServerPlayer)player));
    }

    public void copyFrom(PlayerModData source) {
        this.souls = source.souls;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("souls", souls);
    }
    public void loadNBTData(CompoundTag nbt) {
        this.souls = nbt.getFloat("souls");
    }
}
