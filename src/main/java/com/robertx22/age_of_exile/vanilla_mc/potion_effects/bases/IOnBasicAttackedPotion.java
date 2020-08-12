package com.robertx22.age_of_exile.vanilla_mc.potion_effects.bases;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;

public interface IOnBasicAttackedPotion {
    void onBasicAttacked(StatusEffectInstance instance, LivingEntity source, LivingEntity target);
}
