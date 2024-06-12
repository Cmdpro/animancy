package com.cmdpro.animancy.api;

import com.cmdpro.animancy.entity.SoulKeeper;
import com.cmdpro.animancy.init.EntityInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AnimancyUtil {
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
            return true;
        }
        return false;
    }
}
