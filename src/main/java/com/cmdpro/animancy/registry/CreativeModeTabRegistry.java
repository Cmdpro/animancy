package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeModeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB,
            Animancy.MOD_ID);

    public static Supplier<CreativeModeTab> ITEMS = register("animancy_items", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegistry.SOULMETAL.get()))
                    .title(Component.translatable("creativemodetab.animancy_items")).build());
    public static Supplier<CreativeModeTab> BLOCKS = register("animancy_blocks", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(BlockRegistry.SOULALTAR.get()))
                    .title(Component.translatable("creativemodetab.animancy_blocks")).build());
    private static <T extends CreativeModeTab> Supplier<T> register(final String name,
                                                               final Supplier<? extends T> tab) {
        return CREATIVE_MODE_TABS.register(name, tab);
    }
    public static ResourceKey getKey(CreativeModeTab tab) {
        return BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).get();
    }
}
