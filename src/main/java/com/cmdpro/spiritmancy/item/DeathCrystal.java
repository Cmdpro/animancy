package com.cmdpro.spiritmancy.item;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.ISoulcastersCrystal;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import net.minecraft.world.item.Item;

public class DeathCrystal extends Item implements ISoulcastersCrystal {

    public DeathCrystal(Properties properties) {
        super(properties);
        SpiritmancyUtil.SOULCASTER_CRYSTALS.add(this);
    }
    @Override
    public String getId() {
        return Spiritmancy.MOD_ID + ":death";
    }
}
