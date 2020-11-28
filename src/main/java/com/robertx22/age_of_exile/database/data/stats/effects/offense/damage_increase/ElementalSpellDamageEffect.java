package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ElementalSpellDamageEffect extends BaseDamageIncreaseEffect {

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {

        if (effect.element.equals(Elements.Physical)) {
            return false;
        }

        if (effect.getEffectType()
            .equals(EffectData.EffectTypes.SPELL)) {
            if (stat.getElement() != null && stat.getElement()
                .equals(effect.GetElement())) {
                return true;
            }
        }

        return false;
    }

}
