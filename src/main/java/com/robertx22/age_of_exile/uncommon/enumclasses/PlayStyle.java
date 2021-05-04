package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.database.data.perks.StatAttribute;
import org.apache.commons.lang3.StringUtils;

public enum PlayStyle {

    melee(StatAttribute.STR) {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    },
    ranged(StatAttribute.DEX) {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    },
    magic(StatAttribute.INT) {
        @Override
        public AttackType getAttackType() {
            return AttackType.spell;
        }
    };

    public StatAttribute attribute;

    public String getLocName() {
        return StringUtils.capitalize(name());
    }

    PlayStyle(StatAttribute attribute) {
        this.attribute = attribute;
    }

    public abstract AttackType getAttackType();
}
