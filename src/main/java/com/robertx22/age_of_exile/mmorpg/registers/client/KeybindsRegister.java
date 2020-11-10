package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.mmorpg.Ref;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeybindsRegister {

    public static KeyBinding HUB_SCREEN_KEY = new KeyBinding("Main Hub Screen", GLFW.GLFW_KEY_H, Ref.MOD_NAME);
    public static KeyBinding USE_SPELL_KEY = new KeyBinding("Use Spell", GLFW.GLFW_KEY_R, Ref.MOD_NAME);
    public static KeyBinding CHOOSE_SPELL_KEY = new KeyBinding("Choose Spell", GLFW.GLFW_KEY_V, Ref.MOD_NAME);

    public static void register() {

        KeyBindingHelper.registerKeyBinding(HUB_SCREEN_KEY);
        KeyBindingHelper.registerKeyBinding(CHOOSE_SPELL_KEY);
        KeyBindingHelper.registerKeyBinding(USE_SPELL_KEY);

    }

}
