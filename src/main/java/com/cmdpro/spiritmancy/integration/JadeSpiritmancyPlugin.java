package com.cmdpro.spiritmancy.integration;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.ISoulContainer;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import com.cmdpro.spiritmancy.block.DivinationTable;
import com.cmdpro.spiritmancy.block.entity.DivinationTableBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SoulAltarBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SoulPointBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SpiritTankBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeSpiritmancyPlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(JadeSpiritmancyBlockProvider.INSTANCE, BlockEntity.class);
    }
    public static final ResourceLocation BLOCKPROVIDER = new ResourceLocation(Spiritmancy.MOD_ID, "blockprovider");

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(JadeSpiritmancyBlockProvider.INSTANCE, Block.class);
    }
}