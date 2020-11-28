package com.robertx22.age_of_exile.database.data.stats.effects.offense.damage_increase;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;

public class SpecificWeaponDamageEffect extends BaseDamageIncreaseEffect {

    private SpecificWeaponDamageEffect() {
    }

    public static SpecificWeaponDamageEffect getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
        if (stat instanceof SpecificWeaponDamage) {
            SpecificWeaponDamage weapon = (SpecificWeaponDamage) stat;
            return weapon.weaponType()
                .equals(effect.weaponType);
        }
        return false;
    }

    private static class SingletonHolder {
        private static final SpecificWeaponDamageEffect INSTANCE = new SpecificWeaponDamageEffect();
    }
}