package com.robertx22.age_of_exile.uncommon.utilityclasses;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;

public class ChatUtils {

    public static boolean isChatOpen() {
        return Minecraft.getInstance().screen instanceof ChatScreen;
    }

    public static boolean wasChatOpenRecently() {
        return ClientOnly.ticksSinceChatWasOpened > -5;
    }

}
