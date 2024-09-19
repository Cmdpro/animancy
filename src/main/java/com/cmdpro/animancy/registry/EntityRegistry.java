package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.entity.*;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityRegistry {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Animancy.MOD_ID);
    public static final Supplier<EntityType<SoulKeeper>> SOULKEEPER = register("soul_keeper", () -> EntityType.Builder.of((EntityType.EntityFactory<SoulKeeper>) SoulKeeper::new, MobCategory.MONSTER).sized(0.75f, 1.8f).fireImmune().build(Animancy.MOD_ID + ":" + "soul_keeper"));
    public static final Supplier<EntityType<SoulRitualController>> SOULRITUALCONTROLLER = register("soul_ritual_controller", () -> EntityType.Builder.of((EntityType.EntityFactory<SoulRitualController>) SoulRitualController::new, MobCategory.MISC).sized(0f, 0f).build(Animancy.MOD_ID + ":" + "soul_ritual_controller"));
    public static final Supplier<EntityType<CultistHusk>> CULTIST_HUSK = register("cultist_husk", () -> EntityType.Builder.of((EntityType.EntityFactory<CultistHusk>) CultistHusk::new, MobCategory.MONSTER).sized(0.75f, 1.8f).fireImmune().build(Animancy.MOD_ID + ":" + "cultist_husk"));
    public static final Supplier<EntityType<SoulProjectile>> SOUL_PROJECTILE = register("soul_projectile", () -> EntityType.Builder.of((EntityType.EntityFactory<SoulProjectile>) SoulProjectile::new, MobCategory.MISC).sized(0f, 0f).build(Animancy.MOD_ID + ":" + "soul_projectile"));
    public static final Supplier<EntityType<SpiritArrow>> SPIRIT_ARROW = register("spirit_arrow", () -> EntityType.Builder.of((EntityType.EntityFactory<SpiritArrow>) SpiritArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).build(Animancy.MOD_ID + ":" + "spirit_arrow"));

    private static <T extends EntityType<?>> Supplier<T> register(final String name,
                                                                        final Supplier<? extends T> type) {
        return ENTITY_TYPES.register(name, type);
    }
}
