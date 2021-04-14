package com.robertx22.age_of_exile.uncommon.effectdatas;

public enum AttackType {

    ATTACK("attack", "Attack") {

    },
    SPELL("spell", "Spell") {

    },
    DOT("dot", "DOT") {

    },
    OTHER("other", "Misc") {

    },
    BASIC_ATTACK("basic_attack", "Basic Attack") {

    },
    BASIC_ELE_ATK_DMG("basic_ele_dmg", "Basic Attack Elemental Damage") {

    },
    ALL("all", "All") {

    };

    public String id;

    AttackType(String id, String locname) {
        this.id = id;
        this.locname = locname;
    }

    public boolean isAttack() {
        return this == ATTACK || this == BASIC_ATTACK || this == BASIC_ELE_ATK_DMG;
    }

    public boolean isSpell() {
        return this == SPELL;
    }

    public boolean isDot() {
        return this == DOT;
    }

    public boolean isBasicAttack() {
        return this == BASIC_ATTACK;
    }

    public boolean matches(AttackType other) {

        if (other == ALL || this == ALL) {
            return true;
        }

        return this == other;
    }

    public String locname;

}