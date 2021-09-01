package com.robertx22.age_of_exile.database.data.gear_types.weapons.mechanics;

import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.library_of_exile.registry.IGUID;

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
            .getValue();

        DamageEvent dmg = EventBuilder.ofDamage(data, data.getAttackerEntity(), data.getTargetEntity(), num)
            .setupDamage(AttackType.attack, weptype, data.weaponData.GetBaseGearType().style)
            .setIsBasicAttack()
            .build();

        dmg.Activate();

    }

    public void attack(AttackInformation data) {
        doNormalAttack(data);
    }
}
