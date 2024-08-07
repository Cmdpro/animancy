package com.cmdpro.animancy.block;

import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GoldPillar extends BaseEntityBlock {
    public GoldPillar(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof GoldPillarBlockEntity) {
                ((GoldPillarBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            if (pLevel.getBlockEntity(pPos) instanceof GoldPillarBlockEntity ent) {
                if (ent.itemHandler.getStackInSlot(0).isEmpty()) {
                    ent.itemHandler.setStackInSlot(0, pPlayer.getItemInHand(pHand).copy());
                    pPlayer.getItemInHand(pHand).shrink(pPlayer.getItemInHand(pHand).getCount());
                } else {
                    pPlayer.getInventory().add(ent.itemHandler.getStackInSlot(0));
                    ent.itemHandler.setStackInSlot(0, ItemStack.EMPTY);
                }
                ent.updateBlock();
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GoldPillarBlockEntity(pPos, pState);
    }
}
