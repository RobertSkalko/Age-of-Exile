package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import net.minecraft.item.FoodComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FoodComponent.class)
public class FoodComponentMixin {
    @Inject(method = "isAlwaysEdible", at = @At(value = "HEAD"), cancellable = true)
    private void onKeyPriority(CallbackInfoReturnable<Boolean> ci) {
        if (ModConfig.get().foodEffects.FOOD_IS_ALWAYS_EDIBLE) {
            ci.setReturnValue(true);
        }
    }
}
