package com.robertx22.age_of_exile.vanilla_mc.packets.proxies;

import com.robertx22.age_of_exile.gui.screens.main_hub.MainHubScreen;

public class OpenGuiWrapper {

    public static void openMainHub() {

        net.minecraft.client.MinecraftClient.getInstance()
            .openScreen(new MainHubScreen());

    }

}
