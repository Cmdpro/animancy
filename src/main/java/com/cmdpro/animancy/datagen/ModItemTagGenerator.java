package com.cmdpro.animancy.datagen;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.BlockRegistry;
import com.cmdpro.animancy.registry.ItemRegistry;
import com.cmdpro.animancy.registry.TagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Animancy.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(TagRegistry.Items.SOULDAGGERS)
                .add(ItemRegistry.SOULMETALDAGGER.get())
                .add(ItemRegistry.ANIMAGITE_DAGGER.get());
        this.tag(ItemTags.MUSIC_DISCS)
                .add(ItemRegistry.CRYSTALSOULSMUSICDISC.get())
                .add(ItemRegistry.THESOULSSCREAMMUSICDISC.get())
                .add(ItemRegistry.THESOULSREVENGEMUSICDISC.get());
        this.tag(ItemTags.SOUL_FIRE_BASE_BLOCKS)
                .add(BlockRegistry.ECHOSOIL.get().asItem());
    }
}