package com.cmdpro.spiritmancy.init;

import com.cmdpro.spiritmancy.Spiritmancy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundInit {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Spiritmancy.MOD_ID);

    public static RegistryObject<SoundEvent> SOULKEEPERPHASE1 = registerSoundEvent("soulkeeperphase1");
    public static RegistryObject<SoundEvent> SOULKEEPERPHASE2 = registerSoundEvent("soulkeeperphase2");
    public static RegistryObject<SoundEvent> CRYSTALSOULS = registerSoundEvent("crystalsouls");


    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Spiritmancy.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}