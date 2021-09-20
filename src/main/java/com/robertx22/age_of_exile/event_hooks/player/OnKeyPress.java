package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.gui.screens.character_screen.CharacterScreen;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ChatUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;

public class OnKeyPress {

    public static int cooldown = 0;


    public static void onEndTick(Minecraft mc) {

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (mc.player == null) {
            return;
        }

        if (ChatUtils.wasChatOpenRecently()) {
            return;
        }

        if (KeybindsRegister.HUB_SCREEN_KEY.isDown()) {
            mc.setScreen(new CharacterScreen());
            cooldown = 10;
        } else {

            int number = -1;

            if (KeybindsRegister.SPELL_HOTBAR_1.isDown()) {
                number = 0;
            } else if (KeybindsRegister.SPELL_HOTBAR_2.isDown()) {
                number = 1;
            } else if (KeybindsRegister.SPELL_HOTBAR_3.isDown()) {
                number = 2;
            } else if (KeybindsRegister.SPELL_HOTBAR_4.isDown()) {
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
            }
        }
    }
}
