package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ElementalDamageEffect extends BaseDamageIncreaseEffect {

    private ElementalDamageEffect() {
    }

    public static ElementalDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        if (stat.getElement() == Elements.Elemental) {
            if (effect.element != null && effect.element != Elements.Physical) {
                return true;
            }
        }
        if (effect.element == stat.getElement()) {
            return true;
        }

        return false;
    }

    private static class SingletonHolder {
        private static final ElementalDamageEffect INSTANCE = new ElementalDamageEffect();
    }
}