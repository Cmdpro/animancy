package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Animancy.MOD_ID);

    public static final Supplier<BlockEntityType<SoulAltarBlockEntity>> SOULALTAR =
            register("soul_altar", () ->
                    BlockEntityType.Builder.of(SoulAltarBlockEntity::new,
                            BlockRegistry.SOULALTAR.get()).build(null));
    public static final Supplier<BlockEntityType<GoldPillarBlockEntity>> GOLDPILLAR =
            register("gold_pillar", () ->
                    BlockEntityType.Builder.of(GoldPillarBlockEntity::new,
                            BlockRegistry.GOLDPILLAR.get()).build(null));
    private static <T extends BlockEntityType<?>> Supplier<T> register(final String name,
                                                               final Supplier<? extends T> blockEntity) {
        return BLOCK_ENTITIES.register(name, blockEntity);
    }
}
