package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class DamageToAffectedEffect extends BaseDamageIncreaseEffect {

    ExileEffect eff;

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.target.hasStatusEffect(eff.getStatusEffect());
    }

}