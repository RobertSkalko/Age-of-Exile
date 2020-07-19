package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;

public class KeybindsRegister {

    public static KeyBinding hubScreen = new KeyBinding("Main Hub Screen", GLFW.GLFW_KEY_H, Ref.MOD_NAME);
    public static KeyBinding swapHotbar = new KeyBinding("Swap Spell Hotbar", GLFW.GLFW_KEY_R, Ref.MOD_NAME);

    static String HOTBAR_NAME = Ref.MOD_NAME + " Hotbar";

    static KeyBinding SPELL_1 = hotbar(1, GLFW.GLFW_KEY_1);
    static KeyBinding SPELL_2 = hotbar(2, GLFW.GLFW_KEY_2);
    static KeyBinding SPELL_3 = hotbar(3, GLFW.GLFW_KEY_3);
    static KeyBinding SPELL_4 = hotbar(4, GLFW.GLFW_KEY_4);
    static KeyBinding SPELL_5 = hotbar(5, GLFW.GLFW_KEY_5);

    public static KeyBinding hotbar(int num, int keycode) {
        return new KeyBinding("Spell Hotbar key " + num, keycode, HOTBAR_NAME
        );

    }

    public static HashMap<KeyBinding, Integer> HOTBAR = new HashMap<KeyBinding, Integer>() {{
        put(SPELL_1, 0);
        put(SPELL_2, 1);
        put(SPELL_3, 2);
        put(SPELL_4, 3);
        put(SPELL_5, 4);

    }};

    public static HashMap<Integer, KeyBinding> HOTBAR_BY_NUMBER = new HashMap<Integer, KeyBinding>() {{
        put(0, SPELL_1);
        put(1, SPELL_2);
        put(2, SPELL_3);
        put(3, SPELL_4);
        put(4, SPELL_5);

    }};

    public static void register() {

        KeyBindingHelper.registerKeyBinding(hubScreen);
        KeyBindingHelper.registerKeyBinding(swapHotbar);

        for (KeyBinding entry : HOTBAR.keySet()) {
            KeyBindingHelper.registerKeyBinding(entry);
        }

    }

}
