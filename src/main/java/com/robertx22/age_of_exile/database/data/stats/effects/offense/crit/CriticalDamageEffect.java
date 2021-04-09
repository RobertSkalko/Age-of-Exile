package com.robertx22.age_of_exile.database.data.stats.effects.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class CriticalDamageEffect extends BaseDamageEffect {

    private CriticalDamageEffect() {
    }

    public static CriticalDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.AlmostLast.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.increaseByPercent(data.getAverageValue());
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return
            effect.attackType.isAttack()
                && effect.isCriticalHit()
                && !effect.accuracyCritRollFailed;
    }

    private static class SingletonHolder {
        private static final CriticalDamageEffect INSTANCE = new CriticalDamageEffect();
    }
}
