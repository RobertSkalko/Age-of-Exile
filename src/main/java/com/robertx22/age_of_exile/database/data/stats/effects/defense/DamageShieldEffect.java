package com.robertx22.age_of_exile.database.data.stats.effects.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class DamageShieldEffect extends BaseDamageEffect {

    private DamageShieldEffect() {
    }

    public static DamageShieldEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Target;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.number -= data.getAverageValue();

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return true;
    }

    private static class SingletonHolder {
        private static final DamageShieldEffect INSTANCE = new DamageShieldEffect();
    }
}
