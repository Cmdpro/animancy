package com.cmdpro.animancy.soultypes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;

public class SoulType {
    public SoulType(ResourceLocation icon, Color color, Component name) {
        this.icon = icon;
        this.color = color;
        this.name = name;
    }
    public ResourceLocation icon;
    public Color color;
    public Component name;
}
