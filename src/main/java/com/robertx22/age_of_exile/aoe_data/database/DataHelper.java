package com.robertx22.age_of_exile.aoe_data.database;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.uncommon.effectdatas.interfaces.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;

public class DataHelper {

    public enum Number {
        FULL(1F),
        HALF(0.5F),
        THIRD(0.33F),
        FOURTH(0.25F),
        FIFTH(0.2F);

        public float multi = 1;

        Number(float multi) {
            this.multi = multi;
        }

    }

    public static StatModifier getAttackDamageStat(WeaponTypes weapon, Number num, Elements element) {

        float v1min = 1 * num.multi * weapon.statMulti;
        float v1max = 3 * num.multi * weapon.statMulti;
        float v2min = 3 * num.multi * weapon.statMulti;
        float v2max = 6 * num.multi * weapon.statMulti;

        return new StatModifier(v1min, v1max, v2min, v2max, new AttackDamage(element), ModType.FLAT);
    }

}
