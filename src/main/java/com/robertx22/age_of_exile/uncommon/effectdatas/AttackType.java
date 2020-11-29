package com.robertx22.age_of_exile.uncommon.effectdatas;

public enum AttackType {
    ATTACK("attack", "Attack") {
        @Override
        public boolean isAttack(DamageEffect effect) {
            return EffectUtils.isConsideredAWeaponAttack(effect);
        }
    },

    SPELL("spell", "Spell") {
        @Override
        public boolean isAttack(DamageEffect effect) {
            return EffectUtils.isConsideredASpellAttack(effect);
        }
    },

    ALL("all", "All") {
        @Override
        public boolean isAttack(DamageEffect effect) {
            return true;
        }
    };

    public String id;

    AttackType(String id, String locname) {
        this.id = id;
        this.locname = locname;
    }

    public abstract boolean isAttack(DamageEffect effect);

    public String locname;

}