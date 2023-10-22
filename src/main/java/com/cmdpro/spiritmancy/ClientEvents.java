package com.cmdpro.spiritmancy;

import com.cmdpro.spiritmancy.entity.SoulKeeper;
import com.cmdpro.spiritmancy.init.SoundInit;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.HotbarManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.NamedGuiOverlay;
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
            SoundEvent mus = SoundInit.SOULKEEPERPHASE1.get();
            for (Entity i : mc.level.entitiesForRendering()) {
                if (i instanceof SoulKeeper) {
                    playMusic = true;
                    if (i.getEntityData().get(((SoulKeeper)i).IS_PHASE2)) {
                        mus = SoundInit.SOULKEEPERPHASE2.get();
                    }
                }
            }
            SoundManager manager = mc.getSoundManager();
            if (manager.isActive(music))
            {
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
                    mc.getMusicManager().stopPlaying();
                    music = SimpleSoundInstance.forMusic(mus);
                    manager.play(music);
                }
            }
        }
    }
}
