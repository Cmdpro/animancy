package com.cmdpro.animancy.api;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.block.entity.GoldPillarBlockEntity;
import com.cmdpro.animancy.block.entity.SoulAltarBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class Upgrade {
    public static List<Upgrade> upgradeChecks = new ArrayList<>();
    public abstract void run(SoulAltarBlockEntity ent);
    public static void addDefaultUpgradeChecks() {
        Upgrade.upgradeChecks.add(new Upgrade() {
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
                    if (ent.getLevel().getBlockEntity(i) instanceof GoldPillarBlockEntity ent2) {
                        if (!ent2.itemHandler.getStackInSlot(0).isEmpty()) {
                            ent.getSouls().put(SoulTankItem.getFillTypeLocation(ent2.itemHandler.getStackInSlot(0)), ent.getSouls().getOrDefault(SoulTankItem.getFillTypeLocation(ent2.itemHandler.getStackInSlot(0)), 0f) + SoulTankItem.getFillNumber(ent2.itemHandler.getStackInSlot(0)));
                        }
                    } else {
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
