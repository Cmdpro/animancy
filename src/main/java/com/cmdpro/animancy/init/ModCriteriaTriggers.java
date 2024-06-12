package com.cmdpro.animancy.init;

import com.cmdpro.animancy.criteriatriggers.KillSoulKeeperTrigger;
import com.cmdpro.animancy.criteriatriggers.SpawnSoulKeeperTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class ModCriteriaTriggers {
    public static final KillSoulKeeperTrigger KILLSOULKEEPER = new KillSoulKeeperTrigger();
    public static final SpawnSoulKeeperTrigger SPAWNSOULKEEPER = new SpawnSoulKeeperTrigger();
    public static void register() {
        CriteriaTriggers.register(KILLSOULKEEPER);
        CriteriaTriggers.register(SPAWNSOULKEEPER);
    }
}
