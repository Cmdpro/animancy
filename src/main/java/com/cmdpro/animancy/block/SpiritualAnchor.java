package com.cmdpro.animancy.block;

import com.cmdpro.animancy.moddata.PlayerModDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class SpiritualAnchor extends Block {
    public SpiritualAnchor(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            pPlayer.getCapability(PlayerModDataProvider.PLAYER_MODDATA).ifPresent((data) -> {
                data.soulboundPosition = pPos.above();
                data.soulboundDimension = pLevel.dimension();
                pPlayer.sendSystemMessage(Component.translatable("block.animancy.spiritual_anchor.bind", pPos.above().getX(), pPos.above().getY(), pPos.above().getZ()));
            });
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }
}
