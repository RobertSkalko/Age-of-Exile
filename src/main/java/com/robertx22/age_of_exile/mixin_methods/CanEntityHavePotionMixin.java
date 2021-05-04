package com.robertx22.age_of_exile.mixin_methods;

import com.robertx22.age_of_exile.vanilla_mc.potion_effects.IOneOfATypePotion;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class CanEntityHavePotionMixin {

    public static void hook(LivingEntity en, StatusEffectInstance effect, CallbackInfoReturnable<Boolean> ci) {
        if (!canAddPotion(en, effect.getEffectType())) {
            ci.setReturnValue(false);
        }
    }

    public static boolean canAddPotion(LivingEntity en, StatusEffect effect) {

        try {

            if (effect instanceof IOneOfATypePotion) {
                IOneOfATypePotion one = (IOneOfATypePotion) effect;

                if (one.isOneOfAKind()) {
                    if (en.getStatusEffects()
                        .stream()
                        .anyMatch(x -> {
                            if (x.getEffectType() instanceof IOneOfATypePotion) {
                                IOneOfATypePotion ot = (IOneOfATypePotion) x.getEffectType();
                                if (ot.getOneOfATypeType()
                                    .equals(one.getOneOfATypeType())) {
                                    return true;
                                }
                                if (x.getEffectType()
                                    .equals(effect)) {
                                    return true;
                                }
                            }
                            return false;
                        })) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }
}
