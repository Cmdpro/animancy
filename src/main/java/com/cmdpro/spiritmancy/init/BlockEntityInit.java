package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SoulAltarBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Spiritmancy.MOD_ID);

    public static final RegistryObject<BlockEntityType<SoulAltarBlockEntity>> SOULALTAR =
            BLOCK_ENTITIES.register("soulaltar", () ->
                    BlockEntityType.Builder.of(SoulAltarBlockEntity::new,
                            BlockInit.SOULALTAR.get()).build(null));
    public static final RegistryObject<BlockEntityType<GoldPillarBlockEntity>> GOLDPILLAR =
            BLOCK_ENTITIES.register("goldpillar", () ->
                    BlockEntityType.Builder.of(GoldPillarBlockEntity::new,
                            BlockInit.GOLDPILLAR.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
