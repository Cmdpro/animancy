package com.cmdpro.animancy.config;

import com.cmdpro.animancy.Animancy;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class AnimancyConfig {
    public static final ModConfigSpec COMMON_SPEC;
    public static final AnimancyConfig COMMON;

    static {
        {
            final Pair<AnimancyConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(AnimancyConfig::new);
            COMMON = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
    }
    public AnimancyConfig(ModConfigSpec.Builder builder) {

    }
    private static ModConfigSpec.BooleanValue buildBoolean(ModConfigSpec.Builder builder, String name, String category, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }
    private static ModConfigSpec.IntValue buildInteger(ModConfigSpec.Builder builder, String name, String category, int defaultValue, int min, int max, String comment) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }
    public static void bake(ModConfig config) {
        try {

        } catch (Exception e) {
            Animancy.LOGGER.warn("Failed to load Animancy config");
            e.printStackTrace();
        }
    }
}
