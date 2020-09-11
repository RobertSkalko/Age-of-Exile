package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;

public class SpellConfiguration {

    public boolean swing_arm = false;
    public CastingWeapon castingWeapon = CastingWeapon.ANY_WEAPON;
    public int mana_cost;
    public int times_to_cast = 1;
    public int cast_time_ticks;
    public int cooldown_ticks;

    public SpellConfiguration setSwingArm() {
        this.swing_arm = true;
        return this;
    }

    public static class Builder {

        public static SpellConfiguration instant(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration nonInstant(int mana, int cd, int casttime) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = casttime;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration multiCast(int mana, int cd, int casttime, int times) {
            SpellConfiguration c = new SpellConfiguration();
            c.times_to_cast = times;
            c.cast_time_ticks = casttime;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

    }
}
