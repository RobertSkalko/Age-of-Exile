package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class TotalDamageEffect extends BaseDamageIncreaseEffect {

    private TotalDamageEffect() {
    }

    public static TotalDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return true;
    }

    private static class SingletonHolder {
        private static final TotalDamageEffect INSTANCE = new TotalDamageEffect();
    }
}
