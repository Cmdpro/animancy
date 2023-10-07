package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.api.SoulcasterEffect;
import com.cmdpro.spiritmancy.api.SpiritmancyUtil;
import com.cmdpro.spiritmancy.entity.*;
import com.cmdpro.spiritmancy.init.EntityInit;
import com.cmdpro.spiritmancy.init.ParticleInit;
import com.cmdpro.spiritmancy.particle.SoulParticle;
import com.cmdpro.spiritmancy.recipe.SoulShaperRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.SingleItemRecipe;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = Spiritmancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(EntityInit.SOULKEEPER.get(), SoulKeeper.setAttributes());
    }
    @SubscribeEvent
    public static void registerStuff(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
            helper.register(new ResourceLocation(Spiritmancy.MOD_ID, SoulShaperRecipe.Type.ID), SoulShaperRecipe.Type.INSTANCE);
        });
    }
    @SubscribeEvent
    public static void registerRegistries(NewRegistryEvent event) {
        SpiritmancyUtil.SOULCASTER_EFFECTS_REGISTRY = event.create(new RegistryBuilder<SoulcasterEffect>()
                .setName(new ResourceLocation(Spiritmancy.MOD_ID, "soulcaster_effects")));
    }
}
