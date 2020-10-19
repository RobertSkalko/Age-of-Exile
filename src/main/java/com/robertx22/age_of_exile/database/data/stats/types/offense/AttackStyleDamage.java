package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.offense.AttackStyleDamageEffect;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffects;

public class AttackStyleDamage extends Stat implements IStatEffects {

    public static AttackStyleDamage MELEE = new AttackStyleDamage("melee_dmg", "Melee Damage", AttackPlayStyle.MELEE);
    public static AttackStyleDamage RANGED = new AttackStyleDamage("ranged_dmg", "Ranged Damage", AttackPlayStyle.RANGED);
    public static AttackStyleDamage MAGIC = new AttackStyleDamage("magic_dmg", "Magic Damage", AttackPlayStyle.MAGIC);

    String id;
    transient String name;
    AttackPlayStyle style;
    AttackStyleDamageEffect effect;

    private AttackStyleDamage(String id, String name, AttackPlayStyle style) {
        this.id = id;
        this.name = name;
        this.style = style;

        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;

        this.effect = new AttackStyleDamageEffect(style);
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

    @Override
    public IStatEffect getEffect() {
        return effect;
    }

}

