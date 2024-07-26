package com.cmdpro.animancy.datagen.loot;

import com.cmdpro.animancy.registry.EntityRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
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
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    public ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
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
                        LootItem.lootTableItem(ItemRegistry.PURGATORYINGOT.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(15, 25)))
                )
        ));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityRegistry.ENTITY_TYPES.getEntries().stream().map(RegistryObject::get);
    }
}