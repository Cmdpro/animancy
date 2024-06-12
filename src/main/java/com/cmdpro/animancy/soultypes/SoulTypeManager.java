package com.cmdpro.animancy.soultypes;

import com.cmdpro.animancy.Animancy;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class SoulTypeManager extends SimpleJsonResourceReloadListener {
    protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static SoulTypeManager instance;
    protected SoulTypeManager() {
        super(GSON, "animancy/soultypes");
    }
    public static SoulTypeManager getOrCreateInstance() {
        if (instance == null) {
            instance = new SoulTypeManager();
        }
        return instance;
    }
    public static Map<ResourceLocation, SoulType> types = new HashMap<>();
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        types = new HashMap<>();
        Animancy.LOGGER.info("Adding Animancy Soul Types");
        for (Map.Entry<ResourceLocation, JsonElement> i : pObject.entrySet()) {
            ResourceLocation location = i.getKey();
            if (location.getPath().startsWith("_")) {
                continue;
            }

            try {
                JsonObject obj = i.getValue().getAsJsonObject();
                SoulType type = serializer.read(i.getKey(), obj);
                types.put(i.getKey(), type);
            } catch (IllegalArgumentException | JsonParseException e) {
                Animancy.LOGGER.error("Parsing error loading soul type {}", location, e);
            }
        }
        Animancy.LOGGER.info("Loaded {} soul types", types.size());
    }
    public static SoulTypeSerializer serializer = new SoulTypeSerializer();
}
