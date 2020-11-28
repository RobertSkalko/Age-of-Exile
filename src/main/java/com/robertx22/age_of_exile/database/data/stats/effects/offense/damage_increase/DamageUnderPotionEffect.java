package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import net.minecraft.entity.effect.StatusEffect;

public class DamageUnderPotionEffect extends BaseDamageIncreaseEffect {
    StatusEffect status;

    public DamageUnderPotionEffect(StatusEffect status) {
        this.status = status;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return effect.source.hasStatusEffect(status);
    }

}

