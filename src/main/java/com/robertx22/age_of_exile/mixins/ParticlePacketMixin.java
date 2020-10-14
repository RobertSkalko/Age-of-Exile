package com.robertx22.age_of_exile.mixins;

import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleS2CPacket.class)
public class ParticlePacketMixin {

    // CLIENT only
    @Inject(method = "getCount()I", at = @At(value = "RETURN"), cancellable = true)
    public void hookgetCount(CallbackInfoReturnable<Integer> ci) {
        ci.setReturnValue(1);
    }

}
