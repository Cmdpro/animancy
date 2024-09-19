package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Animancy.MOD_ID);

    public static Supplier<SoundEvent> SOULKEEPERPHASE1 = register("soul_keeper_phase1");
    public static Supplier<SoundEvent> SOULKEEPERPHASE2 = register("soul_keeper_phase2");
    public static Supplier<SoundEvent> CRYSTALSOULS = register("crystal_souls");


    private static Supplier<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Animancy.MOD_ID, name)));
    }
}