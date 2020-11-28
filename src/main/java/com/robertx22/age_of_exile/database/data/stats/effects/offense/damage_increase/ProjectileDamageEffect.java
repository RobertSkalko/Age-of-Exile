package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectUtils;

public class ProjectileDamageEffect extends BaseDamageIncreaseEffect {

    private ProjectileDamageEffect() {
    }

    public static ProjectileDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return EffectUtils.isProjectileAttack(effect);
    }

    private static class SingletonHolder {
        private static final ProjectileDamageEffect INSTANCE = new ProjectileDamageEffect();
    }
}
