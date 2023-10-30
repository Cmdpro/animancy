package com.cmdpro.spiritmancy.item;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.ISoulcastersCrystal;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IceCrystal extends Item implements ISoulcastersCrystal {

    public IceCrystal(Properties properties) {
        super(properties);
        SpiritmancyUtil.SOULCASTER_CRYSTALS.add(this);
    }
    @Override
    public String getId() {
        return Spiritmancy.MOD_ID + ":ice";
    }
}
