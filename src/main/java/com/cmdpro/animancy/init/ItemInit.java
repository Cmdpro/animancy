package com.cmdpro.animancy.init;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.api.SoulTankItem;
import com.cmdpro.animancy.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Animancy.MOD_ID);
    public static final RegistryObject<Item> SOULMETALDAGGER = register("soulmetaldagger", () -> new SwordItem(ModTiers.SCYTHE, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PURGATORYDAGGER = register("purgatorydagger", () -> new SwordItem(ModTiers.PURGATORY, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PURGATORYSWORD = register("purgatorysword", () -> new PurgatorySword(ModTiers.PURGATORY, 5, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SOULMETAL = register("soulmetal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOULALTARITEM = register("soulaltar", () -> new SoulAltarItem(BlockInit.SOULALTAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULCRYSTAL = register("soulcrystal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYSTALSOULSMUSICDISC = register("crystalsoulsmusicdisc", () -> new RecordItem(6, SoundInit.CRYSTALSOULS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2220));
    public static final RegistryObject<Item> THESOULSSCREAMMUSICDISC = register("thesoulsscreammusicdisc", () -> new RecordItem(6, SoundInit.SOULKEEPERPHASE1, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1860));
    public static final RegistryObject<Item> THESOULSREVENGEMUSICDISC = register("thesoulsrevengemusicdisc", () -> new RecordItem(6, SoundInit.SOULKEEPERPHASE2, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1500));
    public static final RegistryObject<Item> PURGATORYINGOT  = register("purgatoryingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SOULTANK  = register("soultank", () -> new SoulTankItem(new Item.Properties()) {
        @Override
        public float getMaxSouls() {
            return 50;
        }
    });
    public static final RegistryObject<Item> STRIDERBOOTS = register("striderboots", () -> new ArmorItem(ModArmorMaterials.STRIDER, ArmorItem.Type.BOOTS, new Item.Properties()));
	
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
    public static final RegistryObject<Item> ANIMANCYGUIDEICON =
            ITEMS.register("animancyguideicon", () -> new Item(new Item.Properties()));
}
