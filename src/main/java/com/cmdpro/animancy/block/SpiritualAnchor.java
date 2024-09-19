package com.cmdpro.animancy.block;

import com.cmdpro.animancy.api.SoulboundLocationData;
import com.cmdpro.animancy.registry.AttachmentTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class SpiritualAnchor extends Block {
    public SpiritualAnchor(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide) {
            pPlayer.setData(AttachmentTypeRegistry.SOULBOUND_LOCATION, new SoulboundLocationData(Optional.of(pLevel.dimension()), Optional.of(pPos.above())));
            pPlayer.sendSystemMessage(Component.translatable("block.animancy.spiritual_anchor.bind", pPos.above().getX(), pPos.above().getY(), pPos.above().getZ()));
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide);
    }

}
