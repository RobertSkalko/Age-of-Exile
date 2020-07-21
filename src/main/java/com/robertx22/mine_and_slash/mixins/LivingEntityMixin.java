package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.mixin_methods.ArmorRedMethod;
import com.robertx22.mine_and_slash.mixin_methods.GearChangeMethod;
import com.robertx22.mine_and_slash.mixin_methods.OnHurtEvent;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.effectdatas.LivingHurtEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/*
  Nothing is done to environmental damage
  By default, entity damage ignores vanilla armor (configurable)
  */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyVariable(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)B", at = @At("HEAD"), argsOnly = true)
    public float on$damage(float amount, DamageSource source) {

        float modified = amount;

        if ((Object) this instanceof LivingEntity) {
            LivingEntity entity = (LivingEntity) (Object) this;

            if (entity.world.isClient) {
                return modified;
            }
            MMORPG.mixinLog("before dmg hook: " + modified);
            modified = OnHurtEvent.onHurtEvent(new LivingHurtEvent(entity, source, modified));
            MMORPG.mixinLog("after dmg hook: " + modified);
        }

        return modified;
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void on$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.world.isClient) {
            return;
        }
        GearChangeMethod.checkGearChanged(entity);
    }

    @Inject(
        method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
        at = @At("HEAD"),
        cancellable = true
    )
    public void onArmorReduction(DamageSource source, float damage, CallbackInfoReturnable<Float> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        ArmorRedMethod.onArmorReduction(source, damage, ci, en);
    }
}
