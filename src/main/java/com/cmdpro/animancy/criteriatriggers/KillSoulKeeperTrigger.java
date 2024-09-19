package com.cmdpro.animancy.criteriatriggers;


import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.registry.CriteriaTriggerRegistry;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import java.util.Optional;

public class KillSoulKeeperTrigger extends SimpleCriterionTrigger<KillSoulKeeperTrigger.KillSoulKeeperTriggerInstance> {

    public static Criterion<KillSoulKeeperTriggerInstance> instance(ContextAwarePredicate player) {
        return CriteriaTriggerRegistry.KILLSOULKEEPER.get().createCriterion(new KillSoulKeeperTriggerInstance(Optional.of(player)));
    }
    public static final Codec<KillSoulKeeperTriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(KillSoulKeeperTriggerInstance::player)
    ).apply(instance, KillSoulKeeperTriggerInstance::new));
    @Override
    public Codec<KillSoulKeeperTriggerInstance> codec() {
        return CODEC;
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, triggerInstance -> true);
    }
    public record KillSoulKeeperTriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleCriterionTrigger.SimpleInstance {
    }
}