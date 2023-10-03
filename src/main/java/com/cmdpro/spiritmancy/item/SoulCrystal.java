package com.cmdpro.spiritmancy.item;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.api.IAmplifierSoulcastersCrystal;
import com.cmdpro.spiritmancy.api.ISoulcastersCrystal;
import net.minecraft.world.item.Item;

public class SoulCrystal extends Item implements IAmplifierSoulcastersCrystal {

    public SoulCrystal(Properties properties) {
        super(properties);
    }

    @Override
    public int getTimesAdd() {
        return 1;
    }
}
