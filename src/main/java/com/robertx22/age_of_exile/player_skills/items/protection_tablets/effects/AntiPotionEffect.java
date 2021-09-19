package com.robertx22.age_of_exile.player_skills.items.protection_tablets.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class AntiPotionEffect extends Effect {

    Effect effect;

    public AntiPotionEffect(Effect eff) {
        super(EffectType.BENEFICIAL, 0);
        this.effect = eff;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.hasEffect(effect)) {
            entity.removeEffect(effect);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}