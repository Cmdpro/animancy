package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.entity.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.Shadow;

public class EntityInit {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Spiritmancy.MOD_ID);
    public static final RegistryObject<EntityType<SpellProjectile>> SPELLPROJECTILE = ENTITY_TYPES.register("spell", () -> EntityType.Builder.of((EntityType.EntityFactory<SpellProjectile>) SpellProjectile::new, MobCategory.MISC).sized(0.25f, 0.25f).build(Spiritmancy.MOD_ID + ":" + "spell"));
}
