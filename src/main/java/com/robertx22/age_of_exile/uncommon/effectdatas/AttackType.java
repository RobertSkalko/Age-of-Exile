package com.robertx22.age_of_exile.uncommon.effectdatas;

import java.util.Arrays;
import java.util.List;

public enum AttackType {

    ATTACK("attack", "Attack") {

    },
    SPELL("spell", "Spell") {

    },
    DOT("dot", "DOT") {

    },
    ALL("all", "All") {

    };

    public static List<AttackType> getAllUsed() {
        return Arrays.asList(ATTACK, SPELL, ALL);
    }

    public String id;

    AttackType(String id, String locname) {
        this.id = id;
        this.locname = locname;
    }

    public boolean isAttack() {
        return this == ATTACK;
    }

    public boolean isSpell() {
        return this == SPELL;
    }

    public boolean isDot() {
        return this == DOT;
    }

    public boolean matches(AttackType other) {

        if (other == ALL || this == ALL) {
            return true;
        }

        return this == other;
    }

    public String locname;

}