package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class NonCritDamageEffect extends BaseDamageEffect {

    private NonCritDamageEffect() {
    }

    public static NonCritDamageEffect getInstance() {
        return NonCritDamageEffect.SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.afterThis(CriticalHitEffect.getInstance()
            .GetPriority());
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.percentIncrease += data.getAverageValue();
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.isCriticalHit();
    }

    private static class SingletonHolder {
        private static final NonCritDamageEffect INSTANCE = new NonCritDamageEffect();
    }
}

