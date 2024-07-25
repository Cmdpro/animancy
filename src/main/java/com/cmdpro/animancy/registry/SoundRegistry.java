package com.cmdpro.animancy.registry;

import com.cmdpro.animancy.Animancy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Animancy.MOD_ID);

    public static RegistryObject<SoundEvent> SOULKEEPERPHASE1 = registerSoundEvent("soul_keeper_phase1");
    public static RegistryObject<SoundEvent> SOULKEEPERPHASE2 = registerSoundEvent("soul_keeper_phase2");
    public static RegistryObject<SoundEvent> CRYSTALSOULS = registerSoundEvent("crystal_souls");
    public static RegistryObject<SoundEvent> SOUL_ALTAR_CRAFTING = registerSoundEvent("soul_altar_crafting");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Animancy.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}