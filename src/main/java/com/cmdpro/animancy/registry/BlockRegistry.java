package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.block.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK,
            Animancy.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = ItemRegistry.ITEMS;
    public static final Supplier<Block> SOULALTAR = register("soul_altar",
            () -> new SoulAltar(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().strength(3.0f)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));
    public static final Supplier<Block> GOLDPILLAR = register("gold_pillar",
            () -> new GoldPillar(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion().strength(3.0f)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));
    public static final Supplier<Block> SPIRITUAL_ANCHOR = register("spiritual_anchor",
            () -> new SpiritualAnchor(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).mapColor(MapColor.COLOR_BLUE)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));
    public static final Supplier<Block> ECHOSOIL = register("echo_soil",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL).mapColor(MapColor.COLOR_BLUE)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));
    public static final Supplier<Block> SOULMETAL_BLOCK = register("soulmetal_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).mapColor(MapColor.COLOR_BLUE)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));
    public static final Supplier<Block> ANIMAGITE_BLOCK = register("animagite_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_LIGHT_BLUE)),
            object -> () -> new BlockItem(object.get(), new Item.Properties()));
    private static <T extends Block> Supplier<T> registerBlock(final String name,
                                                                     final Supplier<? extends T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> Supplier<T> register(final String name, final Supplier<? extends T> block,
                                                                Function<Supplier<T>, Supplier<? extends Item>> item) {
        Supplier<T> obj = registerBlock(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }
}
