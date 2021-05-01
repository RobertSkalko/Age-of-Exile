package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.perks.StatAttribute;

public enum AttackPlayStyle {

    MELEE(StatAttribute.STR) {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    }, RANGED(StatAttribute.DEX) {
        @Override
        public AttackType getAttackType() {
            return AttackType.attack;
        }
    }, MAGIC(StatAttribute.INT) {
        @Override
        public AttackType getAttackType() {
            return AttackType.spell;
        }
    };

    public StatAttribute attribute;

    AttackPlayStyle(StatAttribute attribute) {
        this.attribute = attribute;
    }

    public abstract AttackType getAttackType();
}
