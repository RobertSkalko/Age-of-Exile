package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ManaCost extends Stat {

    private ManaCost() {
        this.min_val = 25;
        this.max_val = 10000;

        this.scaling = StatScaling.NONE;

        this.statEffect = new Effect();
    }

    public static ManaCost getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Affects Mana Cost of all spells.";
    }

    @Override
    public String locNameForLangFile() {
        return "Mana Cost";
    }

    @Override
    public String GUID() {
        return "mana_cost";
    }

    static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.add(SpellModEnum.MANA_COST, data.getAverageValue());
            return effect;
        }

    }

    private static class SingletonHolder {
        private static final ManaCost INSTANCE = new ManaCost();
    }
}
