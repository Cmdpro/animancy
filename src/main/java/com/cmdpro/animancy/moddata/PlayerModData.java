package com.cmdpro.animancy.moddata;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class PlayerModData {
    public BlockPos soulboundPosition;
    public ResourceKey<Level> soulboundDimension;
    public PlayerModData() {
    }

    public void copyFrom(PlayerModData source) {
        soulboundPosition = source.soulboundPosition;
        soulboundDimension = source.soulboundDimension;
    }
    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("hasSoulboundPosition", soulboundPosition != null);
        if (soulboundDimension != null) {
            nbt.putString("soulboundDimension", soulboundDimension.location().toString());
        }
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
        if (nbt.contains("soulboundDimension")) {
            soulboundDimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.tryParse(nbt.getString("soulboundDimension")));
        }
    }
}
