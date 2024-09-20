package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.item.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Animancy.MOD_ID);
    public static final Supplier<Item> SOULSPIN_STAFF = register("soulspin_staff", () -> new SoulspinStaff(new Item.Properties()));
    public static final Supplier<Item> SOULMETALDAGGER = register("soulmetal_dagger", () -> new SwordItem(ModTiers.SOULMETAL, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.IRON, 1, -2.4F))));
    public static final Supplier<Item> ANIMAGITE_DAGGER = register("animagite_dagger", () -> new SwordItem(ModTiers.ANIMAGITE, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.DIAMOND, 1, -2.4F))));
    public static final Supplier<Item> ANIMAGITE_SWORD = register("animagite_sword", () -> new PurgatorySword(ModTiers.ANIMAGITE, new Item.Properties().attributes(SwordItem.createAttributes(Tiers.DIAMOND, 4, -2.4F))));
    public static final Supplier<Item> SOULMETAL = register("soulmetal", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SOUL_STICK = register("soul_stick", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SOULALTARITEM = register("soul_altar", () -> new SoulAltarItem(BlockRegistry.SOULALTAR.get(), new Item.Properties()));
    public static final Supplier<Item> SOULCRYSTAL = register("soul_crystal", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> CRYSTALSOULSMUSICDISC = register("crystal_souls_music_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "crystal_souls")))));
    public static final Supplier<Item> THESOULSSCREAMMUSICDISC = register("the_souls_scream_music_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "the_souls_scream")))));
    public static final Supplier<Item> THESOULSREVENGEMUSICDISC = register("the_souls_revenge_music_disc", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "the_souls_revenge")))));
    public static final Supplier<Item> ANIMAGITE_INGOT  = register("animagite_ingot", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SOULTANK  = register("soul_tank", () -> new SoulTankItem(new Item.Properties()) {
        @Override
        public float getMaxSouls() {
            return 50;
        }
    });
    public static final Supplier<Item> STRIDERBOOTS = register("strider_boots", () -> new ArmorItem(ArmorMaterialRegistry.STRIDER, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final Supplier<Item> SPIRIT_BOW = register("spirit_bow", () -> new SpiritBow(new Item.Properties()));
    public static final Supplier<Item> SOULBOUND_BELL = register("soulbound_bell", () -> new SoulboundBell(new Item.Properties()));
	
    private static <T extends Item> Supplier<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
    public static final Supplier<Item> ANIMANCYGUIDEICON =
            register("animancy_guide_icon", () -> new Item(new Item.Properties()));
}
