package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificElementalWeaponDamage;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class SpecificWeaponElementalDamageEffect extends BaseDamageIncreaseEffect {

    public static SpecificWeaponElementalDamageEffect INSTANCE = new SpecificWeaponElementalDamageEffect();

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        if (stat instanceof SpecificElementalWeaponDamage) {

            SpecificElementalWeaponDamage wepStat = (SpecificElementalWeaponDamage) stat;

            if (wepStat.weaponType()
                .equals(effect.weaponType)) {
                if (effect.isElemental()) {
                    return true;
                }
            }
        }

        return false;
    }

}