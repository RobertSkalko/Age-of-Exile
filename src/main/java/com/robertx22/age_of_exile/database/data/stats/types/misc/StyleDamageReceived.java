package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.AttackPlayStyle;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class StyleDamageReceived extends Stat {

    public static StyleDamageReceived MELEE = new StyleDamageReceived("melee_dmg_received", "Melee Damage Received", AttackPlayStyle.MELEE);
    public static StyleDamageReceived RANGED = new StyleDamageReceived("ranged_dmg_received", "Ranged Damage Received", AttackPlayStyle.RANGED);
    public static StyleDamageReceived MAGIC = new StyleDamageReceived("magic_dmg_received", "Magic Damage Received", AttackPlayStyle.MAGIC);

    String id;
    transient String name;
    AttackPlayStyle style;

    private StyleDamageReceived(String id, String name, AttackPlayStyle style) {
        this.id = id;
        this.name = name;
        this.style = style;

        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;

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
        return "Increases damage dealt to you of this type.";
    }

    private static class Effect extends BaseDamageEffect {

        AttackPlayStyle style;

        public Effect(AttackPlayStyle style) {
            this.style = style;
        }

        @Override
        public DamageEffect activate(DamageEffect effect, StatData data, Stat stat) {
            effect.increaseByPercent(data.getAverageValue());
            return effect;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.style == this.style;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public int GetPriority() {
            return 0;
        }
    }

}

