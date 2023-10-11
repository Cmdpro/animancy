package com.cmdpro.spiritmancy.moddata;

import com.cmdpro.spiritmancy.init.AttributeInit;
import com.cmdpro.spiritmancy.networking.ModMessages;
import com.cmdpro.spiritmancy.networking.packet.PlayerDataSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class PlayerModData {
    public PlayerModData() {
        souls = 0;
    }
    private float souls;
    private int knowledge;
    private BlockPos linkingFrom;
    public float getSouls() {
        return souls;
    }
    public void setSouls(float amount) {
        this.souls = amount;
    }
    public int getKnowledge() {
        return knowledge;
    }
    public void setKnowledge(int amount) {
        this.knowledge = amount;
    }
    public BlockPos getLinkingFrom() {
        return linkingFrom;
    }
    public void setLinkingFrom(BlockPos pos) {
        this.linkingFrom = pos;
    }

    public void updateData(ServerPlayer player) {
        ModMessages.sendToPlayer(new PlayerDataSyncS2CPacket(getSouls(), getKnowledge()), (player));
    }
    public void updateData(Player player) {
        updateData((ServerPlayer)player);
    }
    public static float getMaxSouls(Player player) {
        return (float)player.getAttributeValue(AttributeInit.MAXSOULS.get());
    }
    public void copyFrom(PlayerModData source) {
        this.souls = source.souls;
        this.knowledge = source.knowledge;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putFloat("souls", souls);
        nbt.putInt("knowledge", knowledge);
    }
    public void loadNBTData(CompoundTag nbt) {
        this.souls = nbt.getFloat("souls");
        this.knowledge = nbt.getInt("knowledge");
    }
}
