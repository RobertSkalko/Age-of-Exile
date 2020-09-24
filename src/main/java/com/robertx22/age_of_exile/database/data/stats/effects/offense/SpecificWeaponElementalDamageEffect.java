package com.robertx22.age_of_exile.database.data.stats.effects.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificElementalWeaponDamage;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class SpecificWeaponElementalDamageEffect extends BaseDamageEffect {

    public static SpecificWeaponElementalDamageEffect INSTANCE = new SpecificWeaponElementalDamageEffect();

    @Override
    public int GetPriority() {
        return Priority.Second.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        SpecificElementalWeaponDamage wepStat = (SpecificElementalWeaponDamage) stat;

        if (wepStat.weaponType()
            .equals(effect.weaponType)) {
            if (effect.isElemental()) {
                effect.percentIncrease += data.getAverageValue();
            }
        }

        return effect;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        return stat instanceof SpecificElementalWeaponDamage;
    }

}