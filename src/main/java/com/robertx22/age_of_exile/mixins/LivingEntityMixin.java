package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.database.data.food_effects.FoodEffect;
import com.robertx22.age_of_exile.database.data.food_effects.FoodEffectUtils;
import com.robertx22.age_of_exile.mixin_methods.CanEntityHavePotionMixin;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    //Lnet/minecraft/entity/LivingEntity;eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;

    @Inject(method = "canHaveStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z", at = @At(value = "HEAD"), cancellable = true)
    public void hook(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        CanEntityHavePotionMixin.hook(en, effect, ci);

    }

    @Inject(method = "eatFood(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;", at = @At(value = "HEAD"))
    public void food(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> ci) {
        LivingEntity en = (LivingEntity) (Object) this;
        if (FoodEffectUtils.isFood(stack.getItem())) {
            FoodEffect effect = FoodEffectUtils.getEffect(stack.getItem());
            if (effect != null) {
                effect.apply(en);
            }
        }
    }

}
