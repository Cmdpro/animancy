package com.cmdpro.spiritmancy.block;

import com.cmdpro.spiritmancy.block.entity.DivinationTableBlockEntity;
import com.cmdpro.spiritmancy.block.entity.SoulAltarBlockEntity;
import com.cmdpro.spiritmancy.init.BlockEntityInit;
import com.cmdpro.spiritmancy.init.ItemInit;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class DivinationTable extends BaseEntityBlock {
    public DivinationTable(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =  Block.box(0, 0, 0, 16, 16, 16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }


    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new DivinationTableBlockEntity(pPos, pState);
    }
    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).is(ItemInit.SOULLINKER.get()) || pPlayer.getItemInHand(InteractionHand.OFF_HAND).is(ItemInit.SOULLINKER.get())) {
                return InteractionResult.FAIL;
            }
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof DivinationTableBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (DivinationTableBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, BlockEntityInit.DIVINATIONTABLE.get(),
                DivinationTableBlockEntity::tick);
    }
}
