package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.database.data.value_calc.LeveledValue;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.ArrayList;
import java.util.List;

public class SpellConfiguration {

    public boolean swing_arm = true;
    public CastingWeapon castingWeapon = CastingWeapon.ANY_WEAPON;
    public LeveledValue mana_cost;
    public int times_to_cast = 1;
    public int imbues = 1;
    private int cast_time_ticks = 0;
    public int cooldown_ticks = 20;
    public PlayStyle style = PlayStyle.magic;
    public List<SpellTag> tags = new ArrayList<>();
    public SpellCastType cast_type = SpellCastType.NORMAL;

    public int getCastTimeTicks() {
        return cast_time_ticks;
    }

    public SpellConfiguration setCastType(SpellCastType type) {
        this.cast_type = type;
        return this;
    }

    public boolean isProjectile() {
        return tags.contains(SpellTag.projectile);
    }

    public SpellConfiguration setSwingArm() {
        this.swing_arm = true;
        return this;
    }

    public static class Builder {

        public static SpellConfiguration instant(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.mana_cost = new LeveledValue(0.5F * mana, 1F * mana);
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration arrowSpell(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 20;
            c.mana_cost = new LeveledValue(0.5F * mana, 1F * mana);
            c.cooldown_ticks = cd;
            c.swing_arm = false;
            c.cast_type = SpellCastType.USE_ITEM;
            return c;
        }

        public static SpellConfiguration nonInstant(int mana, int cd, int casttime) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = casttime;
            c.mana_cost = new LeveledValue(0.5F * mana, 1F * mana);
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration multiCast(int mana, int cd, int casttime, int times) {
            SpellConfiguration c = new SpellConfiguration();
            c.times_to_cast = times;
            c.cast_time_ticks = casttime;
            c.mana_cost = new LeveledValue(0.5F * mana, 1F * mana);
            c.cooldown_ticks = cd;
            return c;
        }

    }
}
