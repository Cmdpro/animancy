package com.cmdpro.animancy.datagen;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.BlockRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Animancy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.GOLDPILLAR.get())
                .add(BlockRegistry.SOULALTAR.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.ECHOSOIL.get());
        this.tag(BlockTags.SOUL_FIRE_BASE_BLOCKS)
                .add(BlockRegistry.ECHOSOIL.get());
    }
}
