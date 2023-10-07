package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.entity.SoulKeeper;
import com.cmdpro.spiritmancy.init.SoundInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Spiritmancy.MOD_ID)
public class ClientEvents {
    public static SimpleSoundInstance music;
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        Minecraft mc = Minecraft.getInstance();
        if (event.phase == TickEvent.Phase.END && mc.level != null)
        {
            boolean playMusic = false;
            for (Entity i : mc.level.entitiesForRendering()) {
                if (i instanceof SoulKeeper) {
                    playMusic = true;
                }
            }
            SoundManager manager = mc.getSoundManager();
            if (manager.isActive(music))
            {
                if (!playMusic)
                {
                    manager.stop(music);
                }
            } else {
                if (!manager.isActive(music) && playMusic)
                {
                    SoundEvent mus = SoundInit.BOSS_MUSIC.get();
                    mc.getMusicManager().stopPlaying();
                    music = SimpleSoundInstance.forMusic(mus);
                    manager.play(music);
                }
            }
        }
    }
}
