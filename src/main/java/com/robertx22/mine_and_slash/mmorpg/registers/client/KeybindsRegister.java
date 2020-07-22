package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    public static KeyBinding HUB_SCREEN_KEY = new KeyBinding("Main Hub Screen", GLFW.GLFW_KEY_H, Ref.MOD_NAME);
    public static KeyBinding CHOOSE_SPELL_KEY = new KeyBinding("Swap Spell Hotbar", GLFW.GLFW_KEY_R, Ref.MOD_NAME);

    static String HOTBAR_NAME = Ref.MOD_NAME + " Hotbar";

    public static KeyBinding hotbar(int num, int keycode) {
        return new KeyBinding("Spell Hotbar key " + num, keycode, HOTBAR_NAME
        );

    }

    public static void register() {

        KeyBindingHelper.registerKeyBinding(HUB_SCREEN_KEY);
        KeyBindingHelper.registerKeyBinding(CHOOSE_SPELL_KEY);

    }

}
