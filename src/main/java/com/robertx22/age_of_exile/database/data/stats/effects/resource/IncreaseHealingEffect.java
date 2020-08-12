package com.robertx22.age_of_exile.database.data.stats.effects.resource;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.HealEffect;

public class IncreaseHealingEffect extends BaseHealEffect {

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public HealEffect activate(HealEffect effect, StatData data, Stat stat) {
        effect.number *= data.getMultiplier();

        return effect;
    }

    @Override
    public boolean canActivate(HealEffect effect, StatData data, Stat stat) {
        return true;
    }

}
