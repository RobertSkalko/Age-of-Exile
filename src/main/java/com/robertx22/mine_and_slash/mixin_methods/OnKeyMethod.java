package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.mmorpg.registers.client.KeybindsRegister;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

public class OnKeyMethod {

    public static void onKey(long window, int key, int scancode, CallbackInfo ci) {

        if (KeybindsRegister.swapHotbar.isPressed()) {
            if (Arrays.stream(MinecraftClient.getInstance().options.keysHotbar)
                .anyMatch(x -> x.matchesKey(key, scancode))) {
                ci.cancel();
            }
        }
    }
}
