package com.robertx22.mine_and_slash.mixins;

import com.robertx22.exiled_lib.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/*
  Nothing is done to environmental damage
  By default, entity damage ignores vanilla armor (configurable)
  */
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onKilledBy", at = @At("HEAD"))
    public void on$ondeath(LivingEntity adversary, CallbackInfo ci) {
        LivingEntity victim = (LivingEntity) (Object) this;
        ExileEvents.MOB_DEATH.callEvents(x -> x.onDeath(victim), null);
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void on$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ExileEvents.LIVING_ENTITY_TICK.callEvents(x -> x.onTick(entity), null);
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    public float onmy$damage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ExileEvents.DamageData data = new ExileEvents.DamageData(amount);
        return ExileEvents.DAMAGE_BEFORE_CALC.callEvents(x -> x.onDamage(entity, amount, source, data), data).damage;
    }

    @ModifyVariable(method = "damage", at = @At("TAIL"), argsOnly = true)
    public float onmy$afterdamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ExileEvents.DamageData data = new ExileEvents.DamageData(amount);
        return ExileEvents.DAMAGE_AFTER_CALC.callEvents(x -> x.onDamage(entity, amount, source, data), data).damage;
    }

    /*
    @ModifyVariable(method = "damage", at = @At("LOAD"), name = "amount")
    public float onmy$damageInvoke(float amount, DamageSource source) {
        if (source instanceof MyDamageSource) {
            MyDamageSource my = (MyDamageSource) source;
            if (my.realDamage != amount) {
                System.out.println("Exile dmg was tried to be modified to " + amount + " but i reset it back to " + my.realDamage);
            }
            return my.realDamage;
        }

        return amount;
    }

    @ModifyVariable(method = "applyDamage", at = @At("LOAD"), name = "amount")
    public float onmy$applyDamageInvoke(float amount, DamageSource source) {
        if (source instanceof MyDamageSource) {
            MyDamageSource my = (MyDamageSource) source;
            if (my.realDamage != amount) {
                System.out.println("Exile dmg was tried to be modified to " + amount + " but i reset it back to " + my.realDamage);
            }
            return my.realDamage;
        }

        return amount;
    }

     */

    /*
    @Inject(
        method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F",
        at = @At("HEAD"),
        cancellable = true
    )
    public void onArmorReduction(DamageSource source, float damage, CallbackInfoReturnable<Float> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        ArmorRedMethod.onArmorReduction(source, damage, ci, en);
    }

     */
}
