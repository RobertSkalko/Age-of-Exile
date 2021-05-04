package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.ibm.icu.impl.Assert;

import java.util.Arrays;
import java.util.List;

public enum AttackType {

    attack("attack", "Attack") {

    },
    spell("spell", "Spell") {

    },
    dot("dot", "DOT") {

    },
    all("all", "Any") {

    };

    public static List<AttackType> getAllUsed() {
        return Arrays.asList(attack, spell, all);
    }

    public String id;

    AttackType(String id, String locname) {
        this.id = id;
        this.locname = locname;

        Assert.assrt(id.equals(this.name()));
    }

    public boolean isAttack() {
        return this == attack;
    }

    public boolean isSpell() {
        return this == spell;
    }

    public boolean isDot() {
        return this == dot;
    }

    public boolean matches(AttackType other) {

        if (other == all || this == all) {
            return true;
        }

        return this == other;
    }

    public String locname;

}