package com.robertx22.age_of_exile.database.data.stats.effects.offense.crit;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class CriticalHitEffect extends BaseDamageEffect {

    private CriticalHitEffect() {
    }

    public static CriticalHitEffect getInstance() {
        return SingletonHolder.INSTANCE;
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
        effect.setCrit(true);
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return EffectUtils.isConsideredAWeaponAttack(effect) && RandomUtils.roll(data.getAverageValue());
    }

    private static class SingletonHolder {
        private static final CriticalHitEffect INSTANCE = new CriticalHitEffect();
    }
}
