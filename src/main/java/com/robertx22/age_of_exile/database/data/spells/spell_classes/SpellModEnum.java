package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum SpellModEnum {
    CAST_SPEED(Words.CastSpeed, "cast_speed", -10),
    MANA_COST(Words.ManaCost, "mana_cost", -10),
    AREA(Words.Normal_Gear, "area", -10),
    COOLDOWN(Words.Cooldown, "cooldown", -10),
    DAMAGE(Words.Damage, "damage", 5),
    PROJECTILE_SPEED(Words.ProjectileSpeed, "projectile_speed", 15);

    public Words word;
    public String id;
    public int defaultValue;

    SpellModEnum(Words word, String id, int defaultValue) {
        this.word = word;
        this.id = id;
        this.defaultValue = defaultValue;
    }
}
