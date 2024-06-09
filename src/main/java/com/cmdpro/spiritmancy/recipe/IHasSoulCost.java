package com.cmdpro.spiritmancy.recipe;

import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface IHasSoulCost {
    public Map<ResourceLocation, Float> getSoulCost();
}
