package com.cmdpro.animancy.datagen;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.BlockRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Animancy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        evenSimplerBlockItem(BlockRegistry.SOULALTAR);
        evenSimplerBlockItem(BlockRegistry.GOLDPILLAR);

        simpleItem(ItemRegistry.ANIMANCYGUIDEICON);
        simpleItem(ItemRegistry.CRYSTALSOULSMUSICDISC);
        simpleItem(ItemRegistry.ANIMAGITE_INGOT);
        simpleItem(ItemRegistry.SOULCRYSTAL);
        simpleItem(ItemRegistry.SOULMETAL);
        simpleItem(ItemRegistry.STRIDERBOOTS);
        simpleItem(ItemRegistry.THESOULSREVENGEMUSICDISC);
        simpleItem(ItemRegistry.THESOULSSCREAMMUSICDISC);

        handheldItem(ItemRegistry.ANIMAGITE_DAGGER);
        handheldItem(ItemRegistry.SOULMETALDAGGER);
        handheldItem(ItemRegistry.ANIMAGITE_SWORD);
        handheldItem(ItemRegistry.SOULSPIN_STAFF);
        handheldItem(ItemRegistry.SOUL_STICK);
        handheldItem(ItemRegistry.SOULBOUND_BELL);

        soulTank(ItemRegistry.SOULTANK);
        spiritBow(ItemRegistry.SPIRIT_BOW);
    }
    private ItemModelBuilder soulTank(Supplier<Item> item) {
        withExistingParent("soul_tank1", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling1"));
        withExistingParent("soul_tank2", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling2"));
        withExistingParent("soul_tank3", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling3"));
        withExistingParent("soul_tank4", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling4"));
        withExistingParent("soul_tank5", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling5"));
        withExistingParent("soul_tank6", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling6"));
        withExistingParent("soul_tank7", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling7"));
        withExistingParent("soul_tank8", ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty")).texture("layer1", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_filling8"));
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/soul_tank/soul_tank_empty"))
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.125f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank1"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.25f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank2"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.375f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank3"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.5f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank4"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.625f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank5"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.75f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank6"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 0.895f).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank7"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "fill"), 1).
                model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "soul_tank8"))).end();
    }
    private ItemModelBuilder spiritBow(Supplier<Item> item) {
        withExistingParent("spirit_bow1", ResourceLocation.withDefaultNamespace("item/bow")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/spirit_bow/spirit_bow2"));
        withExistingParent("spirit_bow2", ResourceLocation.withDefaultNamespace("item/bow")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/spirit_bow/spirit_bow3"));
        withExistingParent("spirit_bow3", ResourceLocation.withDefaultNamespace("item/bow")).texture("layer0", ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/spirit_bow/spirit_bow4"));
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/bow")).texture("layer0",
                        ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/spirit_bow/spirit_bow1"))
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pulling"), 1)
                .model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "spirit_bow1"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pulling"), 1)
                .predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pull"), 0.65f)
                .model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "spirit_bow2"))).end()
                .override().predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pulling"), 1)
                .predicate(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "pull"), 0.9f)
                .model(getExistingFile(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "spirit_bow3"))).end();
    }
    private ItemModelBuilder simpleItem(Supplier<Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }
    private ItemModelBuilder simpleItemWithSubdirectory(Supplier<Item> item, String subdirectory) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/" + subdirectory + "/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }
    private ItemModelBuilder flatBlockItemWithTexture(Supplier<Block> item, ResourceLocation texture) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                texture);
    }

    public void evenSimplerBlockItem(Supplier<Block> block) {
        this.withExistingParent(Animancy.MOD_ID + ":" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath(),
                modLoc("block/" + BuiltInRegistries.BLOCK.getKey(block.get()).getPath()));
    }
    public void wallItem(Supplier<Block> block, Supplier<Block> baseBlock) {
        this.withExistingParent(BuiltInRegistries.BLOCK.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(Supplier<Item> item) {
        return withExistingParent(BuiltInRegistries.ITEM.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/" + BuiltInRegistries.ITEM.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(Supplier<Block> item) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"item/" + BuiltInRegistries.BLOCK.getKey(item.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(Supplier<Block> item) {
        return withExistingParent(BuiltInRegistries.BLOCK.getKey(item.get()).getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID,"block/" + BuiltInRegistries.BLOCK.getKey(item.get()).getPath()));
    }
}
