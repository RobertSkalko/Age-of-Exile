package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffectUtils;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.MyDamageSource;
import com.robertx22.age_of_exile.event_hooks.entity.damage.LivingHurtUtils;
import com.robertx22.age_of_exile.mixin_ducks.LivingEntityDuck;
import com.robertx22.age_of_exile.mixin_methods.CanEntityHavePotionMixin;
import com.robertx22.age_of_exile.saveclasses.unit.ResourcesData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements LivingEntityDuck {

    @Shadow
    protected abstract void damageArmor(DamageSource source, float amount);

    @Shadow
    protected abstract void applyDamage(DamageSource source, float amount);

    @Shadow
    protected abstract int getCurrentExperience(PlayerEntity player);

    // ENSURE MY SPECIAL DAMAGE ISNT LOWERED BY ARMOR, ENCHANTS ETC
    @Inject(method = "applyEnchantmentsToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "HEAD"), cancellable = true)
    public void hookench(DamageSource source, float amount, CallbackInfoReturnable<Float> ci) {
        LivingEntity en = (LivingEntity) (Object) this;

        if (!source.isUnblockable()) {
            LivingHurtUtils.damageCurioItems(en, amount);
        }

        if (source instanceof MyDamageSource) {
            ci.setReturnValue(amount);
        }
        if (LivingHurtUtils.isEnviromentalDmg(source)) {
            try {

                if (amount <= 0) {
                    return;
                }

                EntityCap.UnitData data = Load.Unit(en);

                float toReduce = MathHelper.clamp(amount, 0, data.getResources()
                    .getMagicShield());

                if (toReduce <= 0) {
                    return;
                }
                float dmg = amount;
                dmg -= toReduce;

                ResourcesData.Context ms = new ResourcesData.Context(data, en,
                    ResourcesData.Type.MAGIC_SHIELD,
                    toReduce,
                    ResourcesData.Use.SPEND
                );
                data.getResources()
                    .modify(ms);
                ci.setReturnValue(dmg);

            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @Override
    public void myApplyDamage(DamageSource source, float amount) {
        this.applyDamage(source, amount);
    }

    @Override
    public int myGetCurrentExperience(PlayerEntity player) {
        return this.getCurrentExperience(player);
    }
}
