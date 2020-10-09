package com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.event_hooks.entity.damage.DamageEventData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData.EffectTypes;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.HashMap;

public abstract class WeaponMechanic implements IGUID {

    private static HashMap<String, WeaponMechanic> ALL = new HashMap<String, WeaponMechanic>() {
        {
            {
                put(new NormalWeaponMechanic().GUID(), new NormalWeaponMechanic());
            }
        }
    };

    public static WeaponMechanic get(String id) {
        return ALL.getOrDefault(id, new NormalWeaponMechanic());
    }

    protected void doNormalAttack(DamageEventData data) {

        WeaponTypes weptype = data.weaponData.GetBaseGearType()
            .weaponType();

        int num = (int) data.sourceData.getUnit()
            .getCalculatedStat(new AttackDamage(Elements.Physical))
            .getRandomRangeValue();
        DamageEffect dmg = new DamageEffect(
            data.event, data.source, data.target, num, data.sourceData, data.targetData, EffectTypes.BASIC_ATTACK, weptype);

        dmg.setMultiplier(data.multiplier);

        dmg.Activate();

    }

    public void attack(DamageEventData data) {

        data.multiplier = 1;

        doNormalAttack(data);

    }
}
