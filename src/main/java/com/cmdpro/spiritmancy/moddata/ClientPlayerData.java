package com.cmdpro.spiritmancy.moddata;

public class ClientPlayerData {
    private static float souls;

    public static void set(float souls) {
        ClientPlayerData.souls = souls;
    }

    public static float getPlayerSouls() {
        return souls;
    }
}
