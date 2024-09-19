package com.cmdpro.animancy;

import com.cmdpro.animancy.entity.SoulKeeper;
import com.cmdpro.animancy.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(value = Dist.CLIENT, modid = Animancy.MOD_ID)
public class ClientEvents {
    public static SimpleSoundInstance music;
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        Minecraft mc = Minecraft.getInstance();
        boolean playMusic = false;
        SoundEvent mus = SoundRegistry.SOULKEEPERPHASE1.get();
        for (Entity i : mc.level.entitiesForRendering()) {
            if (i instanceof SoulKeeper) {
                playMusic = true;
                if (i.getEntityData().get(((SoulKeeper)i).IS_PHASE2)) {
                    mus = SoundRegistry.SOULKEEPERPHASE2.get();
                }
            }
        }
        SoundManager manager = mc.getSoundManager();
        if (manager.isActive(music))
        {
            mc.getMusicManager().stopPlaying();
            if (!playMusic)
            {
                manager.stop(music);
            }
            if (!music.getLocation().equals(mus.getLocation())) {
                manager.stop(music);
            }
        } else {
            if (!manager.isActive(music) && playMusic)
            {
                music = SimpleSoundInstance.forMusic(mus);
                manager.play(music);
            }
        }
    }
}
