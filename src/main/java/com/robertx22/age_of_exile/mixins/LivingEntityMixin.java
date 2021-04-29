package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.damage_hooks.LivingHurtUtils;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffectUtils;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.mixin_ducks.LivingEntityAccesor;
import com.robertx22.age_of_exile.mixin_methods.CanEntityHavePotionMixin;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityAccesor {

    @Override
    @Invoker("knockback")
    public abstract void myknockback(LivingEntity target);

    @Override
    @Invoker("getHurtSound")
    public abstract SoundEvent myGetHurtSound(DamageSource source);

    @Override
    @Invoker("getSoundVolume")
    public abstract float myGetHurtVolume();

    @Override
    @Invoker("getSoundPitch")
    public abstract float myGetHurtPitch();

    @Shadow
    protected abstract void damageArmor(DamageSource source, float amount);

    @Shadow
    protected abstract void applyDamage(DamageSource source, float amount);

    @ModifyVariable(method = "heal(F)V", at = @At(value = "HEAD"), argsOnly = true, ordinal = 0)
    public float reduceHealPerLevel(float amount, float arg) {
        LivingEntity en = (LivingEntity) (Object) this;
        return HealthUtils.realToVanilla(en, amount);

    }

    @Shadow
    protected abstract int getCurrentExperience(PlayerEntity player);

    // ENSURE MY SPECIAL DAMAGE ISNT LOWERED BY ARMOR, ENCHANTS ETC
    @Inject(method = "applyEnchantmentsToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void hookench(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        if (source instanceof MyDamageSource) {
            ci.setReturnValue(amount);
        }
    }

    @Inject(method = "applyEnchantmentsToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "RETURN"), cancellable = true)
    public void hookenchreturn(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity en = (LivingEntity) (Object) this;

        if (!source.isUnblockable()) {
            LivingHurtUtils.damageCurioItems(en, amount);
        }
        if (source instanceof MyDamageSource) {
            ci.setReturnValue(amount);
        }

    }

    @Inject(method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void hookarmortodmg(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        if (source instanceof MyDamageSource) {
            //damageArmor(source, MathHelper.clamp(amount, 2, 10));
            ci.setReturnValue(amount);
        }
    }
    // ENSURE MY SPECIAL DAMAGE ISNT LOWERED BY ARMOR, ENCHANTS ETC

    @Inject(method = "canHaveStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void hook(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ci) {
        try {
            LivingEntity en = (LivingEntity) (Object) this;
            CanEntityHavePotionMixin.hook(en, effect, ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", at = @At(value = "HEAD"))
    public void food(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
        try {
            LivingEntity en = (LivingEntity) (Object) this;
            if (FoodEffectUtils.isFood(stack.getItem())) {
                FoodEffect effect = FoodEffectUtils.getEffect(stack.getItem());
                if (effect != null) {
                    effect.apply(en);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
