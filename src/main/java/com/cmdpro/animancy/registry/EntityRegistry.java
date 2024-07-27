package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import com.cmdpro.animancy.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Animancy.MOD_ID);
    public static final RegistryObject<EntityType<SoulKeeper>> SOULKEEPER = ENTITY_TYPES.register("soul_keeper", () -> EntityType.Builder.of((EntityType.EntityFactory<SoulKeeper>) SoulKeeper::new, MobCategory.MONSTER).sized(0.75f, 1.8f).fireImmune().build(Animancy.MOD_ID + ":" + "soul_keeper"));
    public static final RegistryObject<EntityType<SoulRitualController>> SOULRITUALCONTROLLER = ENTITY_TYPES.register("soul_ritual_controller", () -> EntityType.Builder.of((EntityType.EntityFactory<SoulRitualController>) SoulRitualController::new, MobCategory.MISC).sized(0f, 0f).build(Animancy.MOD_ID + ":" + "soul_ritual_controller"));
    public static final RegistryObject<EntityType<CultistHusk>> CULTIST_HUSK = ENTITY_TYPES.register("cultist_husk", () -> EntityType.Builder.of((EntityType.EntityFactory<CultistHusk>) CultistHusk::new, MobCategory.MONSTER).sized(0.75f, 1.8f).fireImmune().build(Animancy.MOD_ID + ":" + "cultist_husk"));
    public static final RegistryObject<EntityType<SoulProjectile>> SOUL_PROJECTILE = ENTITY_TYPES.register("soul_projectile", () -> EntityType.Builder.of((EntityType.EntityFactory<SoulProjectile>) SoulProjectile::new, MobCategory.MISC).sized(0f, 0f).build(Animancy.MOD_ID + ":" + "soul_projectile"));
}
