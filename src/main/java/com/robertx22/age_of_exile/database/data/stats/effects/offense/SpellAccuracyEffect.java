package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;

public class SpellAccuracyEffect extends BaseDamageEffect {

    private SpellAccuracyEffect() {
    }

    public static SpellAccuracyEffect getInstance() {
        return SpellAccuracyEffect.SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.data.getNumber(EventData.ACCURACY).number = data.getAverageValue();
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.getAttackType()
            .isSpell();
    }

    private static class SingletonHolder {
        private static final SpellAccuracyEffect INSTANCE = new SpellAccuracyEffect();
    }
}
