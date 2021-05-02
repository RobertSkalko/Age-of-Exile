package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class AttackStyleDamage extends Stat {

    public static AttackStyleDamage MELEE = new AttackStyleDamage("melee_dmg", "Melee Damage", AttackPlayStyle.melee);
    public static AttackStyleDamage RANGED = new AttackStyleDamage("ranged_dmg", "Ranged Damage", AttackPlayStyle.ranged);
    public static AttackStyleDamage MAGIC = new AttackStyleDamage("magic_dmg", "Magic Damage", AttackPlayStyle.magic);

    String id;
    transient String name;
    AttackPlayStyle style;

    private AttackStyleDamage(String id, String name, AttackPlayStyle style) {
        this.id = id;
        this.name = name;
        this.style = style;

        this.scaling = StatScaling.SLOW;
        this.group = StatGroup.Misc;

        this.statEffect = new Effect(style);
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String locDescForLangFile() {
        return "Modifies damage of that type. This includes spells.";
    }

    private static class Effect extends BaseDamageIncreaseEffect {

        AttackPlayStyle style;

        public Effect(AttackPlayStyle style) {
            this.style = style;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.style == this.style;
        }

    }

}

