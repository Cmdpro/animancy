package com.cmdpro.spiritmancy.soultypes;

import com.cmdpro.spiritmancy.Spiritmancy;
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
        super(GSON, "spiritmancy/soultypes");
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
        Spiritmancy.LOGGER.info("Adding Spiritmancy Soul Types");
        for (Map.Entry<ResourceLocation, JsonElement> i : pObject.entrySet()) {
            ResourceLocation location = i.getKey();
            if (location.getPath().startsWith("_")) {
                continue;
            }

            try {
                JsonObject obj = i.getValue().getAsJsonObject();
                SoulType block = serializer.read(i.getKey(), obj);
                types.put(i.getKey(), block);
            } catch (IllegalArgumentException | JsonParseException e) {
                Spiritmancy.LOGGER.error("Parsing error loading soul type {}", location, e);
            }
        }
        Spiritmancy.LOGGER.info("Loaded {} soul types", types.size());
    }
    public static SoulTypeSerializer serializer = new SoulTypeSerializer();
}
