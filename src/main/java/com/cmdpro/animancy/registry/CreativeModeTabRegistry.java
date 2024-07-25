package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabRegistry {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Animancy.MOD_ID);

    public static RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("animancy_items", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegistry.SOULMETAL.get()))
                    .title(Component.translatable("creativemodetab.animancy_items")).build());
    public static RegistryObject<CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register("animancy_blocks", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemRegistry.SOULALTARITEM.get()))
                    .title(Component.translatable("creativemodetab.animancy_blocks")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
