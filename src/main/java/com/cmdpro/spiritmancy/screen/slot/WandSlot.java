package com.cmdpro.spiritmancy.screen.slot;

import com.cmdpro.spiritmancy.api.Wand;
import com.cmdpro.spiritmancy.init.ItemInit;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class WandSlot extends Slot {
    public WandSlot(Container itemHandler, int index, int x, int y) {
        super(itemHandler, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.getItem() instanceof Wand;
    }
}