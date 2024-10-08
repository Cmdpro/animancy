package com.cmdpro.animancy.soultypes;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import java.awt.*;

public class SoulTypeSerializer {
    public SoulType read(ResourceLocation entryId, JsonObject json) {
        if (!json.has("icon")) {
            throw new JsonSyntaxException("Element icon missing in soul type JSON for " + entryId.toString());
        }
        if (!json.has("color")) {
            throw new JsonSyntaxException("Element color missing in soul type JSON for " + entryId.toString());
        }
        if (!json.has("name")) {
            throw new JsonSyntaxException("Element name missing in soul type JSON for " + entryId.toString());
        }
        ResourceLocation icon = ResourceLocation.tryParse(json.get("icon").getAsString());
        JsonObject colorObj = json.get("color").getAsJsonObject();
        Color color = new Color(colorObj.get("r").getAsInt(), colorObj.get("g").getAsInt(), colorObj.get("b").getAsInt(), 255);
        Component name = Component.translatable(json.get("name").getAsString());
        return new SoulType(icon, color, name);
    }
    @Nonnull
    public static SoulType fromNetwork(RegistryFriendlyByteBuf buf) {
        ResourceLocation icon = buf.readResourceLocation();
        int r = buf.readInt();
        int g = buf.readInt();
        int b = buf.readInt();
        Color color = new Color(r, g, b, 255);
        Component name = ComponentSerialization.STREAM_CODEC.decode(buf);
        return new SoulType(icon, color, name);
    }
    public static void toNetwork(RegistryFriendlyByteBuf buf, SoulType type) {
        buf.writeResourceLocation(type.icon);
        buf.writeInt(type.color.getRed());
        buf.writeInt(type.color.getGreen());
        buf.writeInt(type.color.getBlue());
        ComponentSerialization.STREAM_CODEC.encode(buf, type.name);
    }
}