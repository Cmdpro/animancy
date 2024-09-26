package com.cmdpro.animancy.api;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class SoulAltarUpgrade {
    public static List<SoulAltarUpgrade> upgradeChecks = new ArrayList<>();
    public abstract void run(SoulAltarBlockEntity ent);
    public static void addDefaultUpgradeChecks() {
        SoulAltarUpgrade.upgradeChecks.add(new SoulAltarUpgrade() {
            @Override
            public void run(SoulAltarBlockEntity ent) {
                BlockPos[] pillars = {
                        ent.getBlockPos().offset(2, 0, 0),
                        ent.getBlockPos().offset(-2, 0, 0),
                        ent.getBlockPos().offset(0, 0, 2),
                        ent.getBlockPos().offset(0, 0, -2)
                };
                boolean allPillars = true;
                for (BlockPos i : pillars) {
                    if (!(ent.getLevel().getBlockEntity(i) instanceof GoldPillarBlockEntity)) {
                        allPillars = false;
                    }
                }
                if (allPillars) {
                    ent.upgrades.add(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, "basic"));
                }
            }
        });
    }
}
