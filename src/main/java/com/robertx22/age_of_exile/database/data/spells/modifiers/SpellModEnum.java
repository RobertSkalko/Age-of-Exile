package com.robertx22.age_of_exile.database.data.spells.modifiers;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.util.Identifier;

public enum SpellModEnum {
    CAST_SPEED(Words.CastSpeed, "cast_speed"),
    MANA_COST(Words.ManaCost, "mana_cost"),
    COOLDOWN(Words.Cooldown, "cooldown"),
    DAMAGE(Words.Damage, "damage"),
    HEALING(Words.Healing, "healing"),
    PROJECTILE_SPEED(Words.ProjectileSpeed, "projectile_speed");

    public Words word;
    public String id;

    public Identifier getIconLoc() {
        return new Identifier(Ref.MODID, "textures/gui/skill_tree/skill_modifiers/" + id + ".png");
    }

    SpellModEnum(Words word, String id) {
        this.word = word;
        this.id = id;
    }
}
