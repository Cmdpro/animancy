package com.cmdpro.animancy.moddata;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

public class PlayerModData {
    public BlockPos soulboundPosition;
    public PlayerModData() {
    }

    public void copyFrom(PlayerModData source) {
        soulboundPosition = source.soulboundPosition;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("hasSoulboundPosition", soulboundPosition != null);
        if (soulboundPosition != null) {
            nbt.putInt("soulboundPositionX", soulboundPosition.getX());
            nbt.putInt("soulboundPositionY", soulboundPosition.getY());
            nbt.putInt("soulboundPositionZ", soulboundPosition.getZ());
        }
    }
    public void loadNBTData(CompoundTag nbt) {
        boolean hasSoulboundPosition = nbt.getBoolean("hasSoulboundPosition");
        if (hasSoulboundPosition) {
            soulboundPosition = new BlockPos(nbt.getInt("soulboundPositionX"), nbt.getInt("soulboundPositionY"), nbt.getInt("soulboundPositionZ"));
        }
    }
}
