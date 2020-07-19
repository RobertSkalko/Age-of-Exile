package com.robertx22.mine_and_slash.vanilla_mc.packets.proxies;

import com.robertx22.mine_and_slash.gui.screens.main_hub.MainHubScreen;

public class OpenGuiWrapper {

    public static void openMainHub() {

        net.minecraft.client.MinecraftClient.getInstance()
            .openScreen(new MainHubScreen());

    }

}
