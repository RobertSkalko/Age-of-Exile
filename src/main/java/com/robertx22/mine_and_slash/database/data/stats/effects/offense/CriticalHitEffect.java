package com.robertx22.mine_and_slash.database.data.stats.effects.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseAnyEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.EffectData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.interfaces.ICrittable;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.RandomUtils;

public class CriticalHitEffect extends BaseAnyEffect {

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public EffectData activate(EffectData effect, StatData data, Stat stat) {
        ((ICrittable) effect).setCrit(true);

        return effect;
    }

    @Override
    public boolean canActivate(EffectData effect, StatData data, Stat stat) {
        return effect instanceof ICrittable && RandomUtils.roll(data.getAverageValue());
    }

}
