package com.cmdpro.animancy.datagen;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.BlockRegistry;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
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
    }

    private void blockWithItem(Supplier<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
    private void cubeColumn(Supplier<Block> blockRegistryObject) {
        ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get());
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeColumn(loc.getPath(), new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + loc.getPath() + "horizontal"), new ResourceLocation(loc.getNamespace(), ModelProvider.BLOCK_FOLDER + "/deco/ancient/" + loc.getPath())));
    }
}