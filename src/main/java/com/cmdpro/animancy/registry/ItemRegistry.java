package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Animancy.MOD_ID);
    public static final RegistryObject<Item> SOULSPIN_STAFF = register("soulspin_staff", () -> new SoulspinStaff(new Item.Properties()));
    public static final RegistryObject<Item> SOULMETALDAGGER = register("soulmetal_dagger", () -> new SwordItem(ModTiers.SOULMETAL, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> ANIMAGITE_DAGGER = register("animagite_dagger", () -> new SwordItem(ModTiers.ANIMAGITE, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> ANIMAGITE_SWORD = register("animagite_sword", () -> new PurgatorySword(ModTiers.ANIMAGITE, 4, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SOULMETAL = register("soulmetal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOUL_STICK = register("soul_stick", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOULALTARITEM = register("soul_altar", () -> new SoulAltarItem(BlockRegistry.SOULALTAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULCRYSTAL = register("soul_crystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALSOULSMUSICDISC = register("crystal_souls_music_disc", () -> new RecordItem(6, SoundRegistry.CRYSTALSOULS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2220));
    public static final RegistryObject<Item> THESOULSSCREAMMUSICDISC = register("the_souls_scream_music_disc", () -> new RecordItem(6, SoundRegistry.SOULKEEPERPHASE1, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1860));
    public static final RegistryObject<Item> THESOULSREVENGEMUSICDISC = register("the_souls_revenge_music_disc", () -> new RecordItem(6, SoundRegistry.SOULKEEPERPHASE2, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1500));
    public static final RegistryObject<Item> ANIMAGITE_INGOT  = register("animagite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOULTANK  = register("soul_tank", () -> new SoulTankItem(new Item.Properties()) {
        @Override
        public float getMaxSouls() {
            return 50;
        }
    });
    public static final RegistryObject<Item> STRIDERBOOTS = register("strider_boots", () -> new ArmorItem(ModArmorMaterials.STRIDER, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> SPIRIT_BOW  = register("spirit_bow", () -> new SpiritBow(new Item.Properties()));
	
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
    public static final RegistryObject<Item> ANIMANCYGUIDEICON =
            ITEMS.register("animancy_guide_icon", () -> new Item(new Item.Properties()));
}
