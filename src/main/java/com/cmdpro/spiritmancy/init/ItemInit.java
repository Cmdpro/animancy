package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.Wand;
import com.cmdpro.spiritmancy.item.*;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Spiritmancy.MOD_ID);
    public static final RegistryObject<Item> SOULMETALDAGGER = register("soulmetaldagger", () -> new SwordItem(ModTiers.SCYTHE, 1, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> SOULMETAL = register("soulmetal", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPIRITTANK_ITEM = register("spirittank", () -> new SpiritTankItem(BlockInit.SPIRITTANK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULPOINT_ITEM = register("soulpoint", () -> new SoulPointItem(BlockInit.SOULPOINT.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULALTAR_ITEM = register("soulaltar", () -> new SoulAltarItem(BlockInit.SOULALTAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> SOULLINKER = register("soullinker", () -> new SoulLinker(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SOULCRYSTAL = register("soulcrystal", () -> new SoulCrystal(new Item.Properties()));
    public static final RegistryObject<Item> SOULFOCUS = register("soulfocus", () -> new SoulFocus(new Item.Properties()));
    public static final RegistryObject<Item> ICECRYSTAL = register("icecrystal", () -> new IceCrystal(new Item.Properties()));
    public static final RegistryObject<Item> ENDERCRYSTAL = register("endercrystal", () -> new EnderCrystal(new Item.Properties()));
    public static final RegistryObject<Item> FLAMECRYSTAL = register("flamecrystal", () -> new FlameCrystal(new Item.Properties()));
    public static final RegistryObject<Item> LIFECRYSTAL = register("lifecrystal", () -> new LifeCrystal(new Item.Properties()));
    public static final RegistryObject<Item> DEATHCRYSTAL = register("deathcrystal", () -> new DeathCrystal(new Item.Properties()));
    public static final RegistryObject<Item> SOULMETALWAND = register("soulmetalwand", () -> new Wand(new Item.Properties().stacksTo(1), 1));
    public static final RegistryObject<Item> CRYSTALSOULSMUSICDISC = register("crystalsoulsmusicdisc", () -> new RecordItem(6, SoundInit.CRYSTALSOULS, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2220));
    public static final RegistryObject<Item> THESOULSSCREAMMUSICDISC = register("thesoulsscreammusicdisc", () -> new RecordItem(6, SoundInit.SOULKEEPERPHASE1, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1860));
    public static final RegistryObject<Item> THESOULSREVENGEMUSICDISC = register("thesoulsrevengemusicdisc", () -> new RecordItem(6, SoundInit.SOULKEEPERPHASE2, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 1500));
    public static final RegistryObject<Item> FULLSOULCRYSTAL = register("fullsoulcrystal", () -> new FullSoulCrystal(new Item.Properties()));
    public static final RegistryObject<Item> ANCIENTPAGE = register("ancientpage", () -> new AncientPage(new Item.Properties()));
    public static final RegistryObject<Item> SOULBARRIER = register("soulbarrier", () -> new SoulBarrier(new Item.Properties()));
    public static final RegistryObject<Item> SOULORB = register("soulorb", () -> new SoulOrb(new Item.Properties()));
    public static final RegistryObject<Item> SOULTRANSFORMER = register("soultransformer", () -> new SoulTransformer(new Item.Properties()));
    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }
    public static final RegistryObject<Item> SPIRITMANCYGUIDEICON =
            ITEMS.register("spiritmancyguideicon", () -> new Item(new Item.Properties()));
}
