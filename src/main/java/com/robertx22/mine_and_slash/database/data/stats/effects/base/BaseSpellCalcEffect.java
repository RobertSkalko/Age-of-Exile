package com.robertx22.mine_and_slash.database.data.stats.effects.base;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellStatsCalcEffect;

public abstract class BaseSpellCalcEffect extends BaseStatEffect<SpellStatsCalcEffect> {
    public BaseSpellCalcEffect() {
        super(SpellStatsCalcEffect.class);
    }

    @Override
    public boolean canActivate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
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