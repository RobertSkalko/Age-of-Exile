package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.ArrayList;
import java.util.List;

public class SpellConfiguration {

    public boolean swing_arm = true;
    public CastingWeapon castingWeapon = CastingWeapon.ANY_WEAPON;
    public int mana_cost = 0;
    public int cooldown_ticks = 20;
    public PlayStyle style = PlayStyle.magic;
    public List<SpellTag> tags = new ArrayList<>();
    public SpellCastType cast_type = SpellCastType.NORMAL;

    public int imbues = 1;
    public ImbueType imbue_type = ImbueType.ON_RANGED_ATTACK;

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
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

        public static SpellConfiguration arrowImbue(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            // c.swing_arm = false; todo
            c.cast_type = SpellCastType.IMBUE;
            return c;
        }

        public static SpellConfiguration onJumpCritImbue(int mana, int cd, int imbueCount) {
            SpellConfiguration c = new SpellConfiguration();
            c.mana_cost = mana;
            c.imbues = imbueCount;
            c.cooldown_ticks = cd;
            c.imbue_type = ImbueType.ON_CRIT;
            c.cast_type = SpellCastType.IMBUE;
            return c;
        }

        public static SpellConfiguration staffImbue(int mana, int cd, int imbueCount) {
            SpellConfiguration c = new SpellConfiguration();
            c.mana_cost = mana;
            c.imbues = imbueCount;
            c.cooldown_ticks = cd;
            c.imbue_type = ImbueType.STAFF_IMBUE;
            c.cast_type = SpellCastType.IMBUE;
            return c;
        }

        // todo
        public static SpellConfiguration nonInstant(int mana, int cd, int casttime) {
            SpellConfiguration c = new SpellConfiguration();
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            return c;
        }

    }
}
