package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseAnyEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.ICrittable;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class CriticalHitEffect extends BaseAnyEffect {

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
    public EffectData activate(EffectData effect, StatData data, Stat stat) {
        ((ICrittable) effect).setCrit(true);

        return effect;
    }

    @Override
    public boolean canActivate(EffectData effect, StatData data, Stat stat) {
        return effect instanceof ICrittable && RandomUtils.roll(data.getAverageValue());
    }

    private static class SingletonHolder {
        private static final CriticalHitEffect INSTANCE = new CriticalHitEffect();
    }
}
