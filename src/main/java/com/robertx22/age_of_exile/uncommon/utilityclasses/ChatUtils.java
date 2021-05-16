package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;

public class ChatUtils {

    public static boolean isChatOpen() {
        return MinecraftClient.getInstance().currentScreen instanceof ChatScreen;
    }

    public static boolean wasChatOpenRecently() {
        return ClientOnly.ticksSinceChatWasOpened > -5;
    }

}
