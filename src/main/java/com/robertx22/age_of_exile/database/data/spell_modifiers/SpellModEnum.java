package com.robertx22.age_of_exile.database.data.spell_modifiers;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.Identifier;

public enum SpellModEnum {
    CAST_SPEED(Words.CastSpeed, "cast_speed", -10),
    MANA_COST(Words.ManaCost, "mana_cost", -10),
    COOLDOWN(Words.Cooldown, "cooldown", -10),
    DAMAGE(Words.Damage, "damage", 5),
    HEALING(Words.Healing, "healing", 5),
    PROJECTILE_SPEED(Words.ProjectileSpeed, "projectile_speed", 15);

    public Words word;
    public String id;
    public int defaultValue;

    public Identifier getIconLoc() {
        return new Identifier(Ref.MODID, "textures/gui/skill_tree/skill_modifiers/" + id + ".png");
    }

    SpellModEnum(Words word, String id, int defaultValue) {
        this.word = word;
        this.id = id;
        this.defaultValue = defaultValue;
    }
}
