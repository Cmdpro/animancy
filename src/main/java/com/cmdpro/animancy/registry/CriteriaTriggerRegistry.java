package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.criteriatriggers.KillSoulKeeperTrigger;
import com.cmdpro.animancy.criteriatriggers.SpawnSoulKeeperTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class CriteriaTriggerRegistry {
    public static final KillSoulKeeperTrigger KILLSOULKEEPER = new KillSoulKeeperTrigger();
    public static final SpawnSoulKeeperTrigger SPAWNSOULKEEPER = new SpawnSoulKeeperTrigger();
    public static void register() {
        CriteriaTriggers.register(KILLSOULKEEPER);
        CriteriaTriggers.register(SPAWNSOULKEEPER);
    }
}
