package com.cmdpro.animancy.criteriatriggers;


import com.cmdpro.animancy.Animancy;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class SpawnSoulKeeperTrigger extends SimpleCriterionTrigger<SpawnSoulKeeperTrigger.TriggerInstance> {

    public static final ResourceLocation ID = new ResourceLocation(Animancy.MOD_ID, "spawnsoulkeeper");

    public ResourceLocation getId() {
        return ID;
    }

    public SpawnSoulKeeperTrigger.TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
        return new SpawnSoulKeeperTrigger.TriggerInstance(pEntityPredicate);
    }

    public void trigger(ServerPlayer pPlayer) {
        trigger(pPlayer, instance -> instance.test());
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        public TriggerInstance(ContextAwarePredicate pPlayer) {
            super(SpawnSoulKeeperTrigger.ID, pPlayer);
        }
        public boolean test() {
            return true;
        }
    }
}