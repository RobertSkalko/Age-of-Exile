package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.gui.screens.main_hub.MainHubScreen;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class OnKeyPress implements ClientTickEvents.EndTick {

    public static int cooldown = 0;

    @Override

    public void onEndTick(MinecraftClient mc) {

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (mc.player == null) {
            return;
        }
        if (KeybindsRegister.HUB_SCREEN_KEY.isPressed()) {
            mc.openScreen(new MainHubScreen());
            cooldown = 10;
        } else {

        }



        /*else if (KeybindsRegister.swapHotbar.isPressed()) {
            Packets.sendToServer(new CastSpellPacket());
            cooldown = 5;
        }


         */
    }
}
