package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.entity.LivingEntity;

public class ExilePotionEvent extends EffectEvent {

    public static String ID = "on_exile_effect";

    @Override
    public String GUID() {
        return ID;
    }

    public Action action;

    public enum Action {
        GIVE, TAKE;
    }

    public ExilePotionEvent(ExileEffect effect, Spell aura, Action action, LivingEntity caster, LivingEntity target, int tickDuration) {
        super(1, caster, target);

        this.data.setString(EventData.EXILE_EFFECT, effect.GUID());
        this.data.setupNumber(EventData.EFFECT_DURATION_TICKS, tickDuration);
        if (aura != null) {
            this.data.setString(EventData.SPELL, aura.GUID());
        }
        this.action = action;
    }

    @Override
    protected void activate() {

        if (this.data.isCanceled()) {
            return;
        }

    }

}
