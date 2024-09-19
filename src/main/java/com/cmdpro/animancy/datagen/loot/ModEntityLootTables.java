package com.cmdpro.animancy.datagen.loot;

import com.cmdpro.animancy.registry.EntityRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    public ModEntityLootTables(HolderLookup.Provider provider) {
        super(FeatureFlags.REGISTRY.allFlags(), provider);
    }


    @Override
    public void generate() {
        add(EntityRegistry.SOULKEEPER.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().add(
                        LootItem.lootTableItem(ItemRegistry.THESOULSSCREAMMUSICDISC.get()).when(LootItemRandomChanceCondition.randomChance(0.25f))
                )
        ).withPool(
                LootPool.lootPool().add(
                        LootItem.lootTableItem(ItemRegistry.THESOULSREVENGEMUSICDISC.get()).when(LootItemRandomChanceCondition.randomChance(0.25f))
                )
        ).withPool(
                LootPool.lootPool().add(
                        LootItem.lootTableItem(ItemRegistry.ANIMAGITE_INGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(15, 25)))
                )
        ));
        add(EntityRegistry.CULTIST_HUSK.get(), LootTable.lootTable());
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityRegistry.ENTITY_TYPES.getEntries().stream().map(Supplier::get);
    }
}