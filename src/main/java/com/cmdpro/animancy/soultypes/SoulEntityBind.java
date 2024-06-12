package com.cmdpro.animancy.soultypes;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.Map;

public class SoulEntityBind {
    public SoulEntityBind(Map<ResourceLocation, Float> soulTypes, ResourceLocation entity) {
        this.soulTypes = soulTypes;
        this.entity = entity;
    }
    public Map<ResourceLocation, Float> soulTypes;
    public ResourceLocation entity;
}
