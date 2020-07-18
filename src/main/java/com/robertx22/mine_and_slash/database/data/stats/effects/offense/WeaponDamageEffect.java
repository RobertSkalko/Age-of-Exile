package com.robertx22.mine_and_slash.database.data.stats.effects.offense;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.SpecificWeaponDamage;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEffect;

public class WeaponDamageEffect extends BaseDamageEffect {

    @Override
    public int GetPriority() {
        return Priority.First.priority;
    }

    @Override
    public EffectSides Side() {
        return EffectSides.Source;
    }

    @Override
    public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
        effect.number *= data.getMultiplier();
        return effect;
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

}