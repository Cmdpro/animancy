package com.cmdpro.animancy.config;

import com.cmdpro.animancy.Animancy;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class AnimancyConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final AnimancyConfig COMMON;

    static {
        {
            final Pair<AnimancyConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(AnimancyConfig::new);
            COMMON = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
    }
    public AnimancyConfig(ForgeConfigSpec.Builder builder) {

    }
    private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, String category, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }
    private static ForgeConfigSpec.DoubleValue buildDouble(ForgeConfigSpec.Builder builder, String name, String category, double defaultValue, String comment, double min, double max) {
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
