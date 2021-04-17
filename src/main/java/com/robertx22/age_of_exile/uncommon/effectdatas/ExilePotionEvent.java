package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.entity.LivingEntity;

public class ExilePotionEvent extends EffectData {

    public ExileEffect effect;
    public Action action;

    public enum Action {
        GIVE, TAKE;
    }

    public ExilePotionEvent(ExileEffect effect, Action action, LivingEntity caster, LivingEntity target) {
        super(caster, target, Load.Unit(caster), Load.Unit(target));
        this.effect = effect;
        this.action = action;
    }

    @Override
    protected void activate() {

        if (this.canceled) {
            return;
        }

        if (target.isAlive()) {
            this.calculateEffects();
        }
    }

}
