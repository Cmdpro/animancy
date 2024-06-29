package com.cmdpro.animancy.block.entity;

import com.cmdpro.animancy.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GoldPillarBlockEntity extends BlockEntity {
    public GoldPillarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.GOLDPILLAR.get(), pos, state);
        item = ItemStack.EMPTY;
    }
    public void updateBlock() {
        BlockState blockState = level.getBlockState(this.getBlockPos());
        this.level.sendBlockUpdated(this.getBlockPos(), blockState, blockState, 3);
        this.setChanged();
    }
    public ItemStack item;
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("item", item.save(new CompoundTag()));
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        item = ItemStack.of(tag.getCompound("item"));
    }
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt){
        CompoundTag tag = pkt.getTag();
        item = ItemStack.of(tag.getCompound("item"));
    }
    public void drops() {
        SimpleContainer inventory = new SimpleContainer(item);

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket(){
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("item", item.save(new CompoundTag()));
        return tag;
    }
}
