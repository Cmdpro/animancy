package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.item.*;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Spiritmancy.MOD_ID);
    public static final RegistryObject<Item> SOULMETALDAGGER = register("soulmetaldagger", () -> new SwordItem(ModTiers.SCYTHE, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SOULMETAL = register("soulmetal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPIRITTANK_ITEM = register("spirittank", () -> new SpiritTankItem(BlockInit.SPIRITTANK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULPOINT_ITEM = register("soulpoint", () -> new SoulPointItem(BlockInit.SOULPOINT.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULLINKER = register("soullinker", () -> new SoulLinker(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SOULCRYSTAL = register("soulcrystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOULFOCUS = register("soulfocus", () -> new SoulFocus(new Item.Properties()));
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
