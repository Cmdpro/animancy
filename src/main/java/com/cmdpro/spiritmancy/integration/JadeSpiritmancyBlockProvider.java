package com.cmdpro.spiritmancy.integration;

import com.cmdpro.spiritmancy.api.ISoulContainer;
import com.cmdpro.spiritmancy.api.ISoulcastersCrystal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.joml.Math;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.text.DecimalFormat;

public enum JadeSpiritmancyBlockProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    INSTANCE;

    @Override
    public void appendTooltip(
            ITooltip tooltip,
            BlockAccessor accessor,
            IPluginConfig config
    ) {
        if (accessor.getBlockEntity() instanceof ISoulContainer) {
            tooltip.add(Component.translatable("spiritmancy.souls", formatNumber(accessor.getServerData().getFloat("souls")), formatNumber(accessor.getServerData().getFloat("maxsouls"))));
        }
    }
    public String formatNumber(float val) {
        DecimalFormat format = new DecimalFormat("#.0");
        String num = format.format(val);
        if (num.charAt(0) == '.') {
            num = "0" + num;
        }
        return num;
    }

    @Override
    public ResourceLocation getUid() {
        return JadeSpiritmancyPlugin.BLOCKPROVIDER;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof ISoulContainer soulContainer) {
            compoundTag.putFloat("souls", soulContainer.getSouls());
            compoundTag.putFloat("maxsouls", soulContainer.getMaxSouls());
        }
    }
}