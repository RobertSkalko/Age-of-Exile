package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.gui.overlays.spell_hotbar.SpellHotbarOverlay;
import com.robertx22.mine_and_slash.gui.screens.main_hub.MainHubScreen;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import com.robertx22.mine_and_slash.saveclasses.spells.SpellCastingData;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.CastSpellPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class OnKeyPress {

    static boolean down;

    // they said i should use clienttickevent but idk how
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {

        MinecraftClient mc = MinecraftClient.getInstance();

        int key = event.getKey();

        if (event.getAction() == GLFW.GLFW_RELEASE) {

            if (mc.currentScreen == null) {

                if (isCorrectKey(KeybindsRegister.hubScreen, event)) {
                    mc.openScreen(new MainHubScreen());

                } else if (isCorrectKey(KeybindsRegister.swapHotbar, event)) {

                    SpellHotbarOverlay.CURRENT_HOTBAR =
                        SpellHotbarOverlay.CURRENT_HOTBAR == SpellCastingData.Hotbar.FIRST ?
                            SpellCastingData.Hotbar.SECOND : SpellCastingData.Hotbar.FIRST;
                } else {

                    for (KeyBinding entry : KeybindsRegister.HOTBAR.keySet()) {

                        if (isCorrectKey(entry, event)) {
                            MMORPG.sendToServer(new CastSpellPacket(KeybindsRegister.HOTBAR.get(entry),
                                SpellHotbarOverlay.CURRENT_HOTBAR
                            ));
                        }
                    }
                }
            } else {

                if (key == KeybindsRegister.hubScreen.getKey()
                    .getKeyCode()) {

                    if (mc.currentScreen instanceof INamedScreen) {
                        mc.openScreen(null);
                    }

                }

            }
        }

    }

    private static boolean isCorrectKey(KeyBinding keybind, InputEvent.KeyInputEvent event) {

        if (event.getKey() == keybind.getKey()
            .getKeyCode()) {
            if (keybind.getKeyModifier()
                .equals(KeyModifier.getActiveModifier())) {
                return true;
            }
        }

        return false;
    }

}
