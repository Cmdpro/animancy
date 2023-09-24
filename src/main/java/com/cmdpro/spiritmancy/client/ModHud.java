package com.cmdpro.spiritmancy.client;

import com.cmdpro.spiritmancy.Spiritmancy;
import com.cmdpro.spiritmancy.moddata.ClientPlayerData;
import com.cmdpro.spiritmancy.init.ItemInit;
import com.cmdpro.spiritmancy.moddata.PlayerModData;
import com.cmdpro.spiritmancy.moddata.PlayerModDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.fml.ModList;

public class ModHud {
    public static final ResourceLocation soulmeter = new ResourceLocation(Spiritmancy.MOD_ID,"textures/hud/soulmeter.png");
    public static final ResourceLocation soulmeterbg = new ResourceLocation(Spiritmancy.MOD_ID,"textures/hud/soulmeterbg.png");
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void drawHUD(ForgeGui gui, GuiGraphics guiGraphics, float pt, int width,
                               int height) {
        float souls = ClientPlayerData.getPlayerSouls();
        float maxsouls = PlayerModData.MAX_SOULS;
        int x = width / 2;
        int y = height;

        int xpos = 3;
        int ypos = 3;
        if (ModList.get().isLoaded("magicaladventure")) {
            ypos += 15;
        }

        guiGraphics.blit(soulmeterbg, xpos, ypos, 0, 0, 64, 24, 64, 24);
        float w = (26*2)*(souls/maxsouls);
        guiGraphics.blit(soulmeter, xpos+6, ypos, 0, 0, (int)w, 24, 52, 24);
            int textx = xpos + 32;
            int texty = ypos + 8;
            String manaText = (int) Math.floor(souls) + "/" + (int) Math.floor(maxsouls);
            guiGraphics.drawCenteredString(Minecraft.getInstance().font, manaText, textx, texty, 0x7b5480);
    }
}
