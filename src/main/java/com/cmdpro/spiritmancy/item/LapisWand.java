package com.cmdpro.spiritmancy.item;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.ISoulcastersCrystal;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import com.cmdpro.spiritmancy.api.Wand;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LapisWand extends Wand {

    public LapisWand(Properties properties) {
        super(properties, 2);
    }

    @Override
    public float getCastCostMultiplier(Player player, ItemStack stack) {
        float mult = 1/((player.experienceLevel/30)+1);
        if (mult <= 0.25f) {
            mult = 0.25f;
        }
        if (mult >= 1) {
            mult = 1;
        }
        return super.getCastCostMultiplier(player, stack)*mult;
    }
}
