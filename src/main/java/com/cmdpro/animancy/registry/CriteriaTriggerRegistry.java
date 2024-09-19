package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.criteriatriggers.KillSoulKeeperTrigger;
import com.cmdpro.animancy.criteriatriggers.SpawnSoulKeeperTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CriteriaTriggerRegistry {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(BuiltInRegistries.TRIGGER_TYPES,
            Animancy.MOD_ID);
    public static final Supplier<KillSoulKeeperTrigger> KILLSOULKEEPER = register("spawn_soul_keeper", () -> new KillSoulKeeperTrigger());
    public static final Supplier<SpawnSoulKeeperTrigger> SPAWNSOULKEEPER = register("spawn_soul_keeper", () -> new SpawnSoulKeeperTrigger());
    private static <T extends CriterionTrigger<?>> Supplier<T> register(final String name,
                                                                    final Supplier<? extends T> trigger) {
        return TRIGGERS.register(name, trigger);
    }
}
