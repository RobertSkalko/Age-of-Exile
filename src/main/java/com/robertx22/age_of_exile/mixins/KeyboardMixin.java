package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnKeyMethod;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Inject(method = "onKey", at = @At(value = "HEAD"), cancellable = true)
    private void onKeyPriority(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
        OnKeyMethod.onKey(window, key, scancode, ci);
    }
}
