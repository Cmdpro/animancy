package com.cmdpro.animancy.mixin;

import com.cmdpro.animancy.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "canStandOnFluid", at = @At("RETURN"), cancellable = true)
    private void canStandOnFluid(FluidState pFluidState, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = ((LivingEntity)(Object)this);
        if (entity.getItemBySlot(EquipmentSlot.FEET).is(ItemRegistry.STRIDERBOOTS.get()) && !entity.level().getBlockState(BlockPos.containing(entity.position().add(0, 0.5f, 0))).getFluidState().is(FluidTags.LAVA)) {
            if (pFluidState.is(FluidTags.LAVA)) {
                cir.setReturnValue(true);
            }
        }
    }
}
