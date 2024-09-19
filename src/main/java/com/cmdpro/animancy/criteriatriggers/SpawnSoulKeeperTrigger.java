package com.cmdpro.animancy.criteriatriggers;


import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.CriteriaTriggerRegistry;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.util.Optional;

public class SpawnSoulKeeperTrigger extends SimpleCriterionTrigger<SpawnSoulKeeperTrigger.SpawnSoulKeeperTriggerInstance> {

    public static Criterion<SpawnSoulKeeperTriggerInstance> instance(ContextAwarePredicate player) {
        return CriteriaTriggerRegistry.SPAWNSOULKEEPER.get().createCriterion(new SpawnSoulKeeperTriggerInstance(Optional.of(player)));
    }
    public static final Codec<SpawnSoulKeeperTriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(SpawnSoulKeeperTriggerInstance::player)
    ).apply(instance, SpawnSoulKeeperTriggerInstance::new));
    @Override
    public Codec<SpawnSoulKeeperTriggerInstance> codec() {
        return CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }
    public record SpawnSoulKeeperTriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    }
}