package com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
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

    protected void doNormalAttack(AttackInformation data) {

        WeaponTypes weptype = data.weaponData.GetBaseGearType()
            .weaponType();

        int num = (int) data.getAttackerEntityData()
            .getUnit()
            .getCalculatedStat(new AttackDamage(Elements.Physical))
            .getRandomRangeValue();
        DamageEffect dmg = new DamageEffect(
            data, data.getAttackerEntity(), data.getTargetEntity(), num, AttackType.ATTACK, weptype, data.weaponData.GetBaseGearType().style);
        dmg.setIsBasicAttack();

        dmg.Activate();

    }

    public void attack(AttackInformation data) {
        doNormalAttack(data);
    }
}
