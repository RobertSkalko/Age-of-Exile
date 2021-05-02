package com.robertx22.age_of_exile.database.data.stats.types.offense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageIncreaseEffect;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DamageOverTime extends ElementalStat {

    public DamageOverTime(Elements ele) {
        super(ele);
        this.scaling = StatScaling.NONE;
        this.group = StatGroup.Misc;
        this.statEffect = new Effect();

    }

    @Override
    public String locDescForLangFile() {
        return "Affects damage over time, effects like burn etc.";
    }

    @Override
    public String GUID() {
        return element.guidName + "_dot_damage";
    }

    @Override
    public Elements getElement() {
        return element;
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new DamageOverTime(element);
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return element.dmgName + " Damage Over Time";
    }

    private static class Effect extends BaseDamageIncreaseEffect {
        @Override
        public int GetPriority() {
            return Priority.First.priority;
        }

        @Override
        public boolean canActivate(DamageEffect effect, StatData data, Stat stat) {
            return effect.getAttackType()
                .isDot() && stat.getElement()
                .elementsMatch(effect.getElement());
        }
    }

}
