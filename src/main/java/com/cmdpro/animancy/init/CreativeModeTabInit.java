package com.cmdpro.animancy.init;

import com.cmdpro.animancy.Animancy;
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
            Animancy.MOD_ID);

    public static RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("animancyitems", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.SOULMETAL.get()))
                    .title(Component.translatable("creativemodetab.animancyitems")).build());
    public static RegistryObject<CreativeModeTab> BLOCKS = CREATIVE_MODE_TABS.register("animancyblocks", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.SOULALTARITEM.get()))
                    .title(Component.translatable("creativemodetab.animancyblocks")).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
