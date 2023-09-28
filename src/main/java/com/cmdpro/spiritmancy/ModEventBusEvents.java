package com.cmdpro.spiritmancy;

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
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(modid = Spiritmancy.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {

    }
    @SubscribeEvent
    public static void registerStuff(RegisterEvent event) {
        event.register(ForgeRegistries.Keys.RECIPE_TYPES, helper -> {
            helper.register(new ResourceLocation(Spiritmancy.MOD_ID, SoulShaperRecipe.Type.ID), SoulShaperRecipe.Type.INSTANCE);
        });
    }
}
