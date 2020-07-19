package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.mine_and_slash.gui.screens.main_hub.MainHubScreen;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;

import java.util.Map;

public class OnKeyPress implements ClientTickEvents.EndTick {

    @Override
    public void onEndTick(MinecraftClient mc) {

        if (KeybindsRegister.hubScreen.isPressed()) {
            mc.openScreen(new MainHubScreen());

        } else if (KeybindsRegister.swapHotbar.isPressed()) {
            SpellHotbarOverlay.CURRENT_HOTBAR =
                SpellHotbarOverlay.CURRENT_HOTBAR == SpellCastingData.Hotbar.FIRST ?
                    SpellCastingData.Hotbar.SECOND : SpellCastingData.Hotbar.FIRST;
        } else {

            for (Map.Entry<Integer, KeyBinding> entry : KeybindsRegister.HOTBAR_BY_NUMBER.entrySet()) {

                if (entry.getValue()
                    .isPressed()) {
                    MMORPG.sendToServer(new CastSpellPacket(entry.getKey(),
                        SpellHotbarOverlay.CURRENT_HOTBAR
                    ));
                }

            }

        }

    }
}
