package com.cmdpro.spiritmancy.api;

import com.cmdpro.spiritmancy.entity.SoulKeeper;
import com.cmdpro.spiritmancy.init.EntityInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SpiritmancyUtil {
    public static SoulKeeper spawnSoulKeeper(Vec3 pos, Level level) {
        SoulKeeper boss = new SoulKeeper(EntityInit.SOULKEEPER.get(), level);
        boss.setPos(pos);
        boss.spawn();
        level.addFreshEntity(boss);
        return boss;
    }
    public static boolean playerHasAdvancement(Player player, ResourceLocation advancement) {
        if (player instanceof ServerPlayer serverPlayer) {
            Advancement advancement2 = serverPlayer.getServer().getAdvancements().getAdvancement(advancement);
            if (advancement2 != null) {
                if (serverPlayer.getAdvancements().getOrStartProgress(advancement2).isDone()) {
                    return true;
                }
            }
        }
        return false;
    }
}
