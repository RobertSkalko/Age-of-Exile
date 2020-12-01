package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.perks.StatAttribute;

public enum AttackPlayStyle {

    MELEE(StatAttribute.STR) {
        @Override
        public AttackType getAttackType() {
            return AttackType.ATTACK;
        }
    }, RANGED(StatAttribute.DEX) {
        @Override
        public AttackType getAttackType() {
            return AttackType.ATTACK;
        }
    }, MAGIC(StatAttribute.INT) {
        @Override
        public AttackType getAttackType() {
            return AttackType.SPELL;
        }
    };

    public StatAttribute attribute;

    AttackPlayStyle(StatAttribute attribute) {
        this.attribute = attribute;
    }

    public abstract AttackType getAttackType();
}
