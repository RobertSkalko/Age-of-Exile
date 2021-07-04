package com.robertx22.age_of_exile.mmorpg;

import net.minecraft.server.MinecraftServer;

public class MMORPG {

    // DISABLE WHEN PUBLIC BUILD
    public static boolean RUN_DEV_TOOLS = true;

    public static void devToolsLog(String string) {
        if (RUN_DEV_TOOLS) {
            System.out.println(string);
        }
    }

    public static void devToolsErrorLog(String string) {
        if (RUN_DEV_TOOLS) {
            try {
                throw new Exception(string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void logError(String s) {
        try {
            throw new Exception(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MinecraftServer server = null;

}
