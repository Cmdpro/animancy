package com.cmdpro.spiritmancy.moddata;

public class ClientPlayerData {
    private static float souls;
    private static int knowledge;
    private static int ancientknowledge;
    public static void set(float souls, int knowledge, int ancientknowledge) {
        ClientPlayerData.souls = souls;
        ClientPlayerData.knowledge = knowledge;
        ClientPlayerData.ancientknowledge = ancientknowledge;
    }

    public static float getPlayerSouls() {
        return souls;
    }
    public static int getPlayerKnowledge() {
        return knowledge;
    }
    public static int getPlayerAncientKnowledge() {
        return ancientknowledge;
    }
}
