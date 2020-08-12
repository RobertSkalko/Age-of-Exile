package com.robertx22.age_of_exile.database.data.stats.effects.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ChangeDmgElementEffect extends BaseDamageEffect {

    Elements from;
    Elements to;

    public ChangeDmgElementEffect(Elements from, Elements to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.setElement(to);
        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.GetElement() == from;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }
}
