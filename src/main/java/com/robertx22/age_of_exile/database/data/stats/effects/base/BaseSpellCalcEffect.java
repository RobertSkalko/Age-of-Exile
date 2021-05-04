package com.robertx22.age_of_exile.database.data.stats.effects.base;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public abstract class BaseSpellCalcEffect extends InCodeStatEffect<SpellStatsCalculationEvent> {
    public BaseSpellCalcEffect() {
        super(SpellStatsCalculationEvent.class);
    }

    @Override
    public boolean canActivate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
        return true;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public int GetPriority() {
        return 0;
    }
}