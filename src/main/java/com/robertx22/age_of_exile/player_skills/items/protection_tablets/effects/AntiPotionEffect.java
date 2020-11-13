package com.robertx22.age_of_exile.player_skills.items.protection_tablets.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class AntiPotionEffect extends StatusEffect {

    StatusEffect effect;

    public AntiPotionEffect(StatusEffect eff) {
        super(StatusEffectType.BENEFICIAL, 0);
        this.effect = eff;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity.hasStatusEffect(effect)) {
            entity.removeStatusEffect(effect);
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}