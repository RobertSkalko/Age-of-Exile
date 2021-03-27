package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    public static KeyBinding HUB_SCREEN_KEY = new KeyBinding("Main Hub Screen", GLFW.GLFW_KEY_H, Ref.MOD_NAME);

    public static KeyBinding SPELL_HOTBAR_1 = new KeyBinding("Use Spell 1", GLFW.GLFW_KEY_V, Ref.MOD_NAME);
    public static KeyBinding SPELL_HOTBAR_2 = new KeyBinding("Use Spell 2", GLFW.GLFW_KEY_R, Ref.MOD_NAME);
    public static KeyBinding SPELL_HOTBAR_3 = new KeyBinding("Use Spell 3", GLFW.GLFW_KEY_C, Ref.MOD_NAME);
    public static KeyBinding SPELL_HOTBAR_4 = new KeyBinding("Use Spell 4", GLFW.GLFW_KEY_G, Ref.MOD_NAME);

    public static boolean noSpellKeysAreHeld() {
        return !SPELL_HOTBAR_1.isPressed() && !SPELL_HOTBAR_2.isPressed() && !SPELL_HOTBAR_3.isPressed() && !SPELL_HOTBAR_4.isPressed();
    }

    public static KeyBinding getSpellHotbar(int num) {
        int n = num;
        if (num > 3) {
            n -= 4;
        }
        if (n == 0) {
            return SPELL_HOTBAR_1;
        }
        if (n == 1) {
            return SPELL_HOTBAR_2;
        }
        if (n == 2) {
            return SPELL_HOTBAR_3;
        } else {
            return SPELL_HOTBAR_4;
        }

    }

    public static void register() {

        KeyBindingHelper.registerKeyBinding(HUB_SCREEN_KEY);

        KeyBindingHelper.registerKeyBinding(SPELL_HOTBAR_1);
        KeyBindingHelper.registerKeyBinding(SPELL_HOTBAR_2);
        KeyBindingHelper.registerKeyBinding(SPELL_HOTBAR_3);
        KeyBindingHelper.registerKeyBinding(SPELL_HOTBAR_4);

    }

}
