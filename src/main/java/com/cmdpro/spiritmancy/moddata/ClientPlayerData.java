package com.cmdpro.spiritmancy.moddata;

public class ClientPlayerData {
    private static float souls;
    private static float knowledge;

    public static void set(float souls, int knowledge) {
        ClientPlayerData.souls = souls;
        ClientPlayerData.knowledge = knowledge;
    }

    public static float getPlayerSouls() {
        return souls;
    }
    public static float getPlayerKnowledge() {
        return knowledge;
    }
}
