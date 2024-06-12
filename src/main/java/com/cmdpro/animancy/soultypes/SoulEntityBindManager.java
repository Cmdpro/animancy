package com.cmdpro.animancy.soultypes;

import com.cmdpro.animancy.Animancy;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class SoulEntityBindManager extends SimpleJsonResourceReloadListener {
    protected static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static SoulEntityBindManager instance;
    protected SoulEntityBindManager() {
        super(GSON, "animancy/soulentitybinds");
    }
    public static SoulEntityBindManager getOrCreateInstance() {
        if (instance == null) {
            instance = new SoulEntityBindManager();
        }
        return instance;
    }
    public static SoulEntityBind findBindForMob(ResourceLocation mob) {
        for (SoulEntityBind i : binds.values()) {
            if (i.entity.equals(mob)) {
                return i;
            }
        }
        return null;
    }
    public static Map<ResourceLocation, SoulEntityBind> binds = new HashMap<>();
    @Override
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        binds = new HashMap<>();
        Animancy.LOGGER.info("Adding Animancy Soul Entity Binds");
        for (Map.Entry<ResourceLocation, JsonElement> i : pObject.entrySet()) {
            ResourceLocation location = i.getKey();
            if (location.getPath().startsWith("_")) {
                continue;
            }

            try {
                JsonObject obj = i.getValue().getAsJsonObject();
                SoulEntityBind bind = serializer.read(i.getKey(), obj);
                binds.put(i.getKey(), bind);
            } catch (IllegalArgumentException | JsonParseException e) {
                Animancy.LOGGER.error("Parsing error loading soul entity bind {}", location, e);
            }
        }
        Animancy.LOGGER.info("Loaded {} soul entity binds", binds.size());
    }
    public static SoulEntityBindSerializer serializer = new SoulEntityBindSerializer();
}
