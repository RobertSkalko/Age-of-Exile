package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.CanEntityHavePotionMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "canHaveStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void hook(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        CanEntityHavePotionMixin.hook(en, effect, ci);

    }

}
