package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.DontDropGearMethods;
import com.robertx22.age_of_exile.mixin_methods.OnScrollHotbar;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public class PlayerInventoryMixin {

    @Inject(method = "scrollInHotbar", at = @At(value = "HEAD"), cancellable = true)
    private void clickSlot(double scrollAmount, CallbackInfo ci) {
        OnScrollHotbar.clickSlot(scrollAmount, ci);
    }

    @Inject(method = "dropAll", at = @At(value = "HEAD"), cancellable = true)
    private void dontDropGear(CallbackInfo ci) {
        PlayerInventory inv = (PlayerInventory) (Object) this;
        DontDropGearMethods.on(inv, ci);

    }

}
