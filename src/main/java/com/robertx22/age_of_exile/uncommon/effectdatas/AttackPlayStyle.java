package com.robertx22.age_of_exile.uncommon.effectdatas;

public enum AttackPlayStyle {
    MELEE {
        @Override
        public AttackType getAttackType() {
            return AttackType.ATTACK;
        }
    }, RANGED {
        @Override
        public AttackType getAttackType() {
            return AttackType.ATTACK;
        }
    }, MAGIC {
        @Override
        public AttackType getAttackType() {
            return AttackType.SPELL;
        }
    };

    public abstract AttackType getAttackType();
}
