package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import net.minecraft.entity.LivingEntity;

public class ExilePotionEvent extends EffectData {

    public ExileEffect effect;
    public Action action;

    public enum Action {
        GIVE, TAKE;
    }

    public ExilePotionEvent(ExileEffect effect, Action action, LivingEntity caster, LivingEntity target) {
        super(caster, target);
        this.effect = effect;
        this.action = action;
    }

    @Override
    protected void activate() {

        if (this.data.isCanceled()) {
            return;
        }

        if (target.isAlive()) {
            this.calculateEffects();
        }
    }

}
