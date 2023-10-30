package com.cmdpro.spiritmancy.api;

import com.cmdpro.spiritmancy.entity.SoulKeeper;
import com.cmdpro.spiritmancy.init.EntityInit;
import com.cmdpro.spiritmancy.init.SoulcasterEffectInit;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class SpiritmancyUtil {
    public static Supplier<IForgeRegistry<SoulcasterEffect>> SOULCASTER_EFFECTS_REGISTRY = null;
    public static List<Item> SOULCASTER_CRYSTALS = new ArrayList<>();
    public static SoulKeeper spawnSoulKeeper(Vec3 pos, Level level) {
        SoulKeeper boss = new SoulKeeper(EntityInit.SOULKEEPER.get(), level);
        boss.setPos(pos);
        boss.spawn();
        level.addFreshEntity(boss);
        return boss;
    }
}
