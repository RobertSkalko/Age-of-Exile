package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public abstract class BaseDamageIncreaseEffect extends BaseDamageEffect {

    protected BaseDamageIncreaseEffect() {
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
    public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
        effect.increaseByPercent(data.getValue());
        return effect;
    }

}

