package com.cmdpro.animancy.datagen;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Animancy.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(BlockRegistry.ECHOSOIL);
        blockWithItem(BlockRegistry.SOULMETAL_BLOCK);
        blockWithItem(BlockRegistry.ANIMAGITE_BLOCK);
        spiritualAnchor(BlockRegistry.SPIRITUAL_ANCHOR);
    }

    private void blockWithItem(Supplier<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void spiritualAnchor(Supplier<Block> blockRegistryObject) {
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get());
        BlockModelBuilder model = models().withExistingParent(loc.getPath(), ModelProvider.BLOCK_FOLDER + "/cube")
                .texture("west", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_side"))
                .texture("east", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_side"))
                .texture("north", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_side"))
                .texture("down", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_bottom"))
                .texture("up", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_top"))
                .texture("south", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_side"))
                .texture("particle", new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "_side"));
        simpleBlockItem(blockRegistryObject.get(), model);
        simpleBlock(blockRegistryObject.get(), model);
    }
}