package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.mine_and_slash.gui.screens.main_hub.MainHubScreen;
import com.robertx22.mine_and_slash.mmorpg.Packets;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

import java.util.Map;

public class OnKeyPress implements ClientTickEvents.EndTick {

    int cooldown = 0;

    @Override
    public void onEndTick(MinecraftClient mc) {

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        if (mc.player == null) {
            return;
        }
        if (KeybindsRegister.hubScreen.isPressed()) {
            mc.openScreen(new MainHubScreen());
            cooldown = 10;

        } else if (KeybindsRegister.swapHotbar.isPressed()) {
            SpellHotbarOverlay.CURRENT_HOTBAR =
                SpellHotbarOverlay.CURRENT_HOTBAR == SpellCastingData.Hotbar.FIRST ?
                    SpellCastingData.Hotbar.SECOND : SpellCastingData.Hotbar.FIRST;
            cooldown = 20;
        } else {

            for (Map.Entry<Integer, KeyBinding> entry : KeybindsRegister.HOTBAR_BY_NUMBER.entrySet()) {

                if (entry.getValue()
                    .isPressed()) {
                    cooldown = 5;
                    Packets.sendToServer(new CastSpellPacket(entry.getKey(),
                        SpellHotbarOverlay.CURRENT_HOTBAR
                    ));
                }

            }

        }

    }
}
