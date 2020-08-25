package com.robertx22.age_of_exile.mobs;

import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class OnTickRandomSpeedBoost {

    public static void onTick(LivingEntity en, int everyxticks, int chance) {

        if (en.age % everyxticks == 0) {
            if (en.getAttacking() == null) {
                return;
            }
            if (RandomUtils.roll(chance)) {
                en.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, everyxticks / 2, 1));
            }
        }

    }
}
