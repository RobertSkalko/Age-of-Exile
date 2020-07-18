package com.robertx22.mine_and_slash.vanilla_mc.packets.proxies;

import com.robertx22.mine_and_slash.gui.screens.main_hub.MainHubScreen;

import net.minecraftforge.fml.DistExecutor;

public class OpenGuiWrapper {

    public static void openMainHub() {
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
            net.minecraft.client.MinecraftClient.getInstance()
                .openScreen(new MainHubScreen());
        });
    }

}
