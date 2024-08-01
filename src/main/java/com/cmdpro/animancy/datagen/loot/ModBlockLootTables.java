package com.cmdpro.animancy.datagen.loot;

import com.cmdpro.animancy.registry.BlockRegistry;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(BlockRegistry.SOULALTAR.get());
        dropSelf(BlockRegistry.ECHOSOIL.get());
        dropSelf(BlockRegistry.GOLDPILLAR.get());
        dropSelf(BlockRegistry.SOULMETAL_BLOCK.get());
        dropSelf(BlockRegistry.ANIMAGITE_BLOCK.get());
    }
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map((block) -> (Block)block.get()).filter((block) -> {
            if (block instanceof LiquidBlock) {
                return false;
            }
            return true;
        })::iterator;
    }
}