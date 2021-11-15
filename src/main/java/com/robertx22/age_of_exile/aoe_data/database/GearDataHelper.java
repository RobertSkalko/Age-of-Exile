package com.robertx22.age_of_exile.aoe_data.database;

import com.robertx22.age_of_exile.database.data.StatModifier;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.AttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;

public interface GearDataHelper {

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

    public enum ArmorType {
        LIGHT(0.7F, 0.8F),
        MEDIUM(0.8F, 0.9F),
        HEAVY(1F, 1F);

        public float armorMulti = 1;
        public float hpMulti = 1;

        ArmorType(float armorMulti, float hpMulti) {
            this.armorMulti = armorMulti;
            this.hpMulti = hpMulti;
        }

        public float getMulti(ArmorStat stat) {
            if (stat == ArmorStat.ARMOR) {
                return armorMulti;
            }
            if (stat == ArmorStat.HEALTH) {
                return hpMulti;
            }
            return 1;
        }
    }

    public enum ArmorSlot {
        HELMET(0.5F),
        CHEST(1F),
        PANTS(0.8F),
        BOOTS(0.5F);

        public float multi;

        ArmorSlot(float multi) {
            this.multi = multi;
        }
    }

    public enum ArmorStat {
        ARMOR(6, 15, Armor.getInstance()),
        ELE_DEF(6, 15, new ElementalResist(Elements.Elemental)),
        DODGE(6, 15, DodgeRating.getInstance()),
        HEALTH(4, 12, Health.getInstance());

        public float min;
        public float max;
        public Stat stat;

        ArmorStat(float min, float max, Stat stat) {
            this.min = min;
            this.max = max;
            this.stat = stat;
        }
    }

    public default StatModifier getStat(ArmorStat stat, ArmorType type, ArmorSlot slot) {

        float v1min = stat.min * type.getMulti(stat) * slot.multi;
        float v1max = stat.max * type.getMulti(stat) * slot.multi;

        return new StatModifier(v1min, v1max, stat.stat, ModType.FLAT);
    }

    public default StatModifier getAttackDamageStat(WeaponTypes weapon, Number num, Elements element) {

        float v1min = 2 * num.multi * weapon.statMulti;
        float v1max = 4 * num.multi * weapon.statMulti;

        return new StatModifier(v1min, v1max, new AttackDamage(element), ModType.FLAT);
    }

}
