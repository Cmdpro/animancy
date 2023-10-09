package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeModeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,
            Spiritmancy.MOD_ID);

    public static RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("spiritmancy_items", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.SOULMETAL.get()))
                    .title(Component.translatable("creativemodetab.spiritmancy_items")).build());
    public static RegistryObject<CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register("spiritmancy_blocks", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.SOULPOINT_ITEM.get()))
                    .title(Component.translatable("creativemodetab.spiritmancy_blocks")).build());
    public static RegistryObject<CreativeModeTab> FULLCRYSTALS = CREATIVE_MODE_TABS.register("spiritmancy_fullcrystals", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.FULLSOULCRYSTAL.get()))
                    .title(Component.translatable("creativemodetab.spiritmancy_fullcrystals")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
