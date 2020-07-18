package com.robertx22.mine_and_slash.database.data.stats.effects.resource;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseHealEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.HealEffect;

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
