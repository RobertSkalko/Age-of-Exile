package com.robertx22.age_of_exile.database.data.spells.components;

import com.robertx22.age_of_exile.database.data.skill_gem.SkillGemTag;
import com.robertx22.age_of_exile.database.data.spells.PlayerAction;
import com.robertx22.age_of_exile.database.data.spells.SpellCastType;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;

import java.util.ArrayList;
import java.util.List;

public class SpellConfiguration {

    public boolean swing_arm = false;
    public CastingWeapon castingWeapon = CastingWeapon.ANY_WEAPON;
    public int mana_cost;
    public int times_to_cast = 1;
    private int cast_time_ticks = 0;
    public int cooldown_ticks = 20;
    public AttackPlayStyle style = AttackPlayStyle.magic;
    public List<SkillGemTag> tags = new ArrayList<>();
    public SpellCastType cast_type = SpellCastType.NORMAL;
    public List<PlayerAction> actions_needed = new ArrayList<>();
    public boolean scale_mana_cost_to_player_lvl = false;

    public boolean isTechnique() {
        return !actions_needed.isEmpty();
    }

    public int getCastTimeTicks() {
        return cast_time_ticks;
    }

    public SpellConfiguration setRequireActions(List<PlayerAction> list) {
        this.actions_needed = list;
        return this;
    }

    public SpellConfiguration setCastType(SpellCastType type) {
        this.cast_type = type;
        return this;
    }

    public boolean isProjectile() {
        return tags.contains(SkillGemTag.projectile);
    }

    public SpellConfiguration setSwingArm() {
        this.swing_arm = true;
        return this;
    }

    public SpellConfiguration setScaleManaToPlayer() {
        this.scale_mana_cost_to_player_lvl = true;
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

        public static SpellConfiguration arrowSpell(int mana, int cd) {
            SpellConfiguration c = new SpellConfiguration();
            c.cast_time_ticks = 0;
            c.mana_cost = mana;
            c.cooldown_ticks = cd;
            c.cast_type = SpellCastType.USE_ITEM;
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
