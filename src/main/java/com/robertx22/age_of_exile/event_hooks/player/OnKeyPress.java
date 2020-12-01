package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.gui.screens.main_hub.MainHubScreen;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

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

            int number = -1;

            if (KeybindsRegister.SPELL_HOTBAR_1.isPressed()) {
                number = 0;
            } else if (KeybindsRegister.SPELL_HOTBAR_2.isPressed()) {
                number = 1;
            } else if (KeybindsRegister.SPELL_HOTBAR_3.isPressed()) {
                number = 2;
            } else if (KeybindsRegister.SPELL_HOTBAR_4.isPressed()) {
                number = 3;
            } else {
                number -= 500; // dont cast
            }

            if (Screen.hasShiftDown()) {
                number += 4;
            }
            if (number > -1) {
                // todo make sure its not lagging servers
                Packets.sendToServer(new TellServerToCastSpellPacket(number));
                cooldown = 5;
            }
        }
    }
}
