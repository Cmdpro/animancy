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

public class ModHud {
    public static final ResourceLocation manabar = new ResourceLocation(Spiritmancy.MOD_ID,"textures/hud/manabar.png");
    public static final ResourceLocation manabarbg = new ResourceLocation(Spiritmancy.MOD_ID,"textures/hud/manabarbg.png");
    public static final ResourceLocation connectionbar = new ResourceLocation(Spiritmancy.MOD_ID,"textures/hud/connectionbar.png");
    public static final ResourceLocation connectionbarbg = new ResourceLocation(Spiritmancy.MOD_ID,"textures/hud/connectionbarbg.png");
    private static final Minecraft minecraft = Minecraft.getInstance();

    public static void drawHUD(ForgeGui gui, GuiGraphics guiGraphics, float pt, int width,
                               int height) {
        float mana = ClientPlayerData.getPlayerSouls();
        float maxmana = PlayerModData.MAX_SOULS;
        int x = width / 2;
        int y = height;

        int xpos = 5;
        int ypos = 5;

        guiGraphics.blit(manabarbg, xpos-2, ypos-2, 0, 0, 85, 12, 85, 12);
        float w = 80*(mana/maxmana);
        float w2 = 81*(mana/maxmana);
        guiGraphics.blit(manabar, xpos, ypos, 0, 0, (int)w2, 8, 80, 8);
        int textx = xpos;
        int texty = ypos;
        String manaText = (int)Math.floor(mana) + "/" + (int)Math.floor(maxmana);
        guiGraphics.drawString(Minecraft.getInstance().font, manaText, textx, texty, 0x00328f);
    }
}
