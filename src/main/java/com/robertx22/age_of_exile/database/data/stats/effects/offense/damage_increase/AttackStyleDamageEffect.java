package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class AttackStyleDamageEffect extends BaseDamageIncreaseEffect {

    AttackPlayStyle style;

    public AttackStyleDamageEffect(AttackPlayStyle style) {
        this.style = style;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.style == this.style;
    }

}
