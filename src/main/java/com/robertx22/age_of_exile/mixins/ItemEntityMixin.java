package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.config.forge.ModConfig;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public class ItemEntityMixin {

    @Inject(method = "onPlayerCollision", at = @At(value = "HEAD"), cancellable = true)
    private void dontPickupGearWhenDead(PlayerEntity player, CallbackInfo ci) {
        try {
            if (ModConfig.get().Server.SAVE_GEAR_AND_HOTBAR_ON_DEATH) {
                if (!player.isAlive()) {
                    ci.cancel();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
