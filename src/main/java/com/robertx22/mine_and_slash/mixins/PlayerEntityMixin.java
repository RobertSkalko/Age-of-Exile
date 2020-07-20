package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.event_hooks.TODO.OnMobDeathDrops;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "onKilledOther(Lnet/minecraft/entity/LivingEntity;)V", at = @At("HEAD"))
    public void on$ondeath(LivingEntity other, CallbackInfo ci) {
        OnMobDeathDrops.mobOnDeathDrop(other);
    }
}
