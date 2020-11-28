package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class DayDamageEffect extends BaseDamageIncreaseEffect {
    private DayDamageEffect() {
    }

    public static DayDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.source.world.isDay();
    }

    private static class SingletonHolder {
        private static final DayDamageEffect INSTANCE = new DayDamageEffect();
    }
}

