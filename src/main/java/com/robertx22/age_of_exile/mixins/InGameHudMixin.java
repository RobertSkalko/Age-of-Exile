package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.OnKeyMethod;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "renderHotbar", at = @At(value = "HEAD"), cancellable = true)
    public void on$renderHotbar(float f, MatrixStack matrixStack, CallbackInfo ci) {
        if (OnKeyMethod.isSelectingSpells()) {
            ci.cancel();
        }
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getHealth()F"))
    public float on$getHealth(PlayerEntity entity) {
        float multi = HealthUtils.getCurrentHealth(entity) / HealthUtils.getMaxHealth(entity);
        return 20F * multi;
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"))
    public double on$getHealthGeneric(PlayerEntity entity, EntityAttribute attribute) {
        if (attribute == EntityAttributes.GENERIC_MAX_HEALTH) {
            return 20;
        }
        return entity.getAttributeBaseValue(attribute);
    }

    @Inject(method = "getHeartCount", at = @At(value = "TAIL"), cancellable = true)
    public void on$getMaxHealth(LivingEntity entity, CallbackInfoReturnable<Integer> ci) {
        ci.setReturnValue(10);
    }

}
