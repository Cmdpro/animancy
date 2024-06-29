package com.cmdpro.animancy;

import com.cmdpro.animancy.entity.SoulKeeper;
import com.cmdpro.animancy.registry.SoundRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Animancy.MOD_ID)
public class ClientEvents {
    public static SimpleSoundInstance music;
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END && mc.level != null)
        {
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
}
