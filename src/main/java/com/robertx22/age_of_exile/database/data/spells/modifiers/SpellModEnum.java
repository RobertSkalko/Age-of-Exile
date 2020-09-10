package com.robertx22.age_of_exile.database.data.spells.modifiers;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum SpellModEnum {
    CAST_SPEED(Words.CastSpeed),
    MANA_COST(Words.ManaCost),
    COOLDOWN(Words.Cooldown),
    DAMAGE(Words.Damage),
    HEALING(Words.Healing),
    PROJECTILE_SPEED(Words.ProjectileSpeed);

    public Words word;

    SpellModEnum(Words word) {
        this.word = word;
    }
}
