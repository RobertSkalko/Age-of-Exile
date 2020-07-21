package com.robertx22.mine_and_slash.mixins;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    // TODO

    @Redirect(method = "renderStatusBars",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getMaxHealth()F")
    )
    public final float on$getMaxHealth(LivingEntity entity) {
        return 20;
    }


    /*
    WorldRenderer
    @Inject(method = "getHeartCount", at = @At("RETURN"), cancellable = true)

    public void on$limitHeartCount(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        int hp = cir.getReturnValueI(); // I don't remember what this method is actually called...
        hp = Math.min(hp, 10);
        cir.setReturnValue(hp);        // ^
    }


     */
}
