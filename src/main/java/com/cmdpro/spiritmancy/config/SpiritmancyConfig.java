package com.cmdpro.spiritmancy.config;

import com.cmdpro.spiritmancy.Spiritmancy;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class SpiritmancyConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final SpiritmancyConfig COMMON;

    static {
        {
            final Pair<SpiritmancyConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(SpiritmancyConfig::new);
            COMMON = specPair.getLeft();
            COMMON_SPEC = specPair.getRight();
        }
    }
    public SpiritmancyConfig(ForgeConfigSpec.Builder builder) {
        builder.push("blocks");
        soulAltarSoulDrainValue = buildDouble(builder, "soulAltarSoulDrain", "blocks", 0.1, "How much souls should the soul altar drain per tick?", 0, 1000000);
        divinationTableSoulCostValue = buildDouble(builder, "divinationTableSoulCost", "blocks", 2, "How much souls does the divination table cost to convert an item to study progress?", 0, 1000000);
        builder.pop();
        builder.push("items");
        soulmetalDaggerSoulMultiplierValue = buildDouble(builder, "soulmetalDaggerSoulMultiplier", "items", 1, "How much should the souls obtained from killing a mob be multiplied by when using a soulmetal dagger?", 0, 1000000);
        purgatoryDaggerSoulMultiplierValue = buildDouble(builder, "purgatoryDaggerSoulMultiplier", "items", 2, "How much should the souls obtained from killing a mob be multiplied by when using a purgatory dagger?", 0, 1000000);
        soulOrbSoulMultiplierValue = buildDouble(builder, "soulOrbSoulMultiplier", "items", 1, "How much should the souls obtained from killing a mob be multiplied by when a soul orb is equipped?", 0, 1000000);
        soulBarrierSoulCostValue = buildDouble(builder, "soulBarrierSoulCost", "items", 1, "How much souls should the soul barrier cost to protect you from damage?", 0, 1000000);
        soulBarrierDamageMultiplierValue = buildDouble(builder, "soulBarrierDamageMultiplier", "items", 0.9, "What should the damage be multiplied by when the soul barrier protects you from damage?", 0, 1);
        soulBoosterSoulCostValue = buildDouble(builder, "soulBoosterSoulCost", "items", 2, "How much souls should the soul booster cost to double jump?", 0, 1000000);
        soulTransformerHealthPerSoulValue = buildDouble(builder, "soulTransformerHealthPerSoul", "items", 0.25, "How much health should be given per soul with the soul transformer equipped?", 0, 1000000);
        builder.pop();
        builder.push("other");
        baseHealthPerSoulValue = buildDouble(builder, "baseHealthPerSoul", "other", 10, "How much health is required per soul when stealing a mobs soul?", 1, 1000000);
    }
    private static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String name, String category, boolean defaultValue, String comment) {
        return builder.comment(comment).translation(name).define(name, defaultValue);
    }
    private static ForgeConfigSpec.DoubleValue buildDouble(ForgeConfigSpec.Builder builder, String name, String category, double defaultValue, String comment, double min, double max) {
        return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
    }
    public static double soulAltarSoulDrain = 0.1;
    public static double divinationTableSoulCost = 2;
    public static double soulmetalDaggerSoulMultiplier = 1;
    public static double purgatoryDaggerSoulMultiplier = 2;
    public static double soulOrbSoulMultiplier = 1;
    public static double soulBarrierSoulCost = 1;
    public static double soulBarrierDamageMultiplier = 0.9;
    public static double soulBoosterSoulCost = 2;
    public static double soulTransformerHealthPerSoul = 0.25;
    public static double baseHealthPerSoul = 10;
    public final ForgeConfigSpec.DoubleValue soulAltarSoulDrainValue;
    public final ForgeConfigSpec.DoubleValue soulmetalDaggerSoulMultiplierValue;
    public final ForgeConfigSpec.DoubleValue purgatoryDaggerSoulMultiplierValue;
    public final ForgeConfigSpec.DoubleValue soulOrbSoulMultiplierValue;
    public final ForgeConfigSpec.DoubleValue divinationTableSoulCostValue;
    public final ForgeConfigSpec.DoubleValue soulBarrierSoulCostValue;
    public final ForgeConfigSpec.DoubleValue soulBarrierDamageMultiplierValue;
    public final ForgeConfigSpec.DoubleValue soulBoosterSoulCostValue;
    public final ForgeConfigSpec.DoubleValue soulTransformerHealthPerSoulValue;
    public final ForgeConfigSpec.DoubleValue baseHealthPerSoulValue;
    public static void bake(ModConfig config) {
        try {
            soulAltarSoulDrain = COMMON.soulAltarSoulDrainValue.get();
            divinationTableSoulCost = COMMON.divinationTableSoulCostValue.get();
            soulmetalDaggerSoulMultiplier = COMMON.soulmetalDaggerSoulMultiplierValue.get();
            purgatoryDaggerSoulMultiplier = COMMON.purgatoryDaggerSoulMultiplierValue.get();
            soulOrbSoulMultiplier = COMMON.soulOrbSoulMultiplierValue.get();
            soulBarrierSoulCost = COMMON.soulBarrierSoulCostValue.get();
            soulBarrierDamageMultiplier = COMMON.soulBarrierDamageMultiplierValue.get();
            soulBoosterSoulCost = COMMON.soulBoosterSoulCostValue.get();
            soulTransformerHealthPerSoul = COMMON.soulTransformerHealthPerSoulValue.get();
            baseHealthPerSoul = COMMON.baseHealthPerSoulValue.get();
        } catch (Exception e) {
            Spiritmancy.LOGGER.warn("Failed to load Spiritmancy config");
            e.printStackTrace();
        }
    }
}
