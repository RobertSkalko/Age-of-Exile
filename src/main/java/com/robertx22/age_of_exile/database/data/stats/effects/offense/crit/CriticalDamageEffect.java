package com.robertx22.age_of_exile.database.data.stats.effects.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectUtils;

public class CriticalDamageEffect extends BaseDamageEffect {

    private CriticalDamageEffect() {
    }

    public static CriticalDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
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
        return
            EffectUtils.isConsideredAWeaponAttack(effect)
                && effect.isCriticalHit()
                && !effect.accuracyCritRollFailed;
    }

    private static class SingletonHolder {
        private static final CriticalDamageEffect INSTANCE = new CriticalDamageEffect();
    }
}
