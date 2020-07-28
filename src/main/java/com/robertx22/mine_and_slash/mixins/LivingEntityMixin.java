package com.robertx22.mine_and_slash.mixins;

import com.robertx22.exile_lib.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onKilledBy", at = @At("HEAD"))
    public void hookOnDeath(LivingEntity adversary, CallbackInfo ci) {
        LivingEntity victim = (LivingEntity) (Object) this;
        ExileEvents.MOB_DEATH.callEvents(new ExileEvents.OnMobDeath(victim));
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void hookOnTick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        ExileEvents.LIVING_ENTITY_TICK.callEvents(new ExileEvents.OnEntityTick(entity));
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    public float hookOnDamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return ExileEvents.DAMAGE_BEFORE_CALC.callEvents(new ExileEvents.OnDamageEntity(source, amount, entity)).damage;
    }

    @ModifyVariable(method = "damage", at = @At("TAIL"), argsOnly = true)
    public float hookAfterDamage(float amount, DamageSource source) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return ExileEvents.DAMAGE_AFTER_CALC.callEvents(new ExileEvents.OnDamageEntity(source, amount, entity)).damage;
    }

}
