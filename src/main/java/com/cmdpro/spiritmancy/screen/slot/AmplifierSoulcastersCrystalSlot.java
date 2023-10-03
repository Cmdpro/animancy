package com.cmdpro.spiritmancy.screen.slot;

import com.cmdpro.spiritmancy.api.IAmplifierSoulcastersCrystal;
import com.cmdpro.spiritmancy.api.ISoulcastersCrystal;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AmplifierSoulcastersCrystalSlot extends Slot {
    public AmplifierSoulcastersCrystalSlot(Container itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof IAmplifierSoulcastersCrystal;
    }
}