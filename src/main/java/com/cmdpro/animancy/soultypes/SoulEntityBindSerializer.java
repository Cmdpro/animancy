package com.cmdpro.animancy.soultypes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SoulEntityBindSerializer {
    public SoulEntityBind read(ResourceLocation entryId, JsonObject json) {
        if (!json.has("soulTypes")) {
            throw new JsonSyntaxException("Element soulType missing in entry JSON for " + entryId.toString());
        }
        if (!json.has("entity")) {
            throw new JsonSyntaxException("Element entity missing in entry JSON for " + entryId.toString());
        }
        Map<ResourceLocation, Float> soulTypes = new HashMap<>();
        for (JsonElement i : json.getAsJsonArray("soulTypes")) {
            JsonObject obj = i.getAsJsonObject();
            soulTypes.put(ResourceLocation.tryParse(obj.get("type").getAsString()), obj.get("amount").getAsFloat());
        }
        ResourceLocation entity = ResourceLocation.tryParse(json.get("entity").getAsString());
        return new SoulEntityBind(soulTypes, entity);
    }
    @Nonnull
    public static SoulEntityBind fromNetwork(FriendlyByteBuf buf) {
        Map<ResourceLocation, Float> soulTypes = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readFloat);
        ResourceLocation entity = buf.readResourceLocation();
        return new SoulEntityBind(soulTypes, entity);
    }
    public static void toNetwork(FriendlyByteBuf buf, SoulEntityBind type) {
        buf.writeMap(type.soulTypes, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeFloat);
        buf.writeResourceLocation(type.entity);
    }
}