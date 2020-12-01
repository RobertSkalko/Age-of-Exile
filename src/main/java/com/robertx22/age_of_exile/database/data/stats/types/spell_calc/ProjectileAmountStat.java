package com.robertx22.age_of_exile.database.data.stats.types.spell_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ProjectileAmountStat extends Stat {

    private ProjectileAmountStat() {
        this.max_val = 10;
        this.statEffect = new Effect();
        this.scaling = StatScaling.NONE;
    }

    public static ProjectileAmountStat getInstance() {
        return ProjectileAmountStat.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Adds to number of projectiles of certain spells.";
    }

    @Override
    public String locNameForLangFile() {
        return "More Spell Projectiles";
    }

    @Override
    public String GUID() {
        return "more_proj";
    }

    private static class Effect extends BaseSpellCalcEffect {

        @Override
        public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
            effect.data.extraProjectiles += data.getAverageValue();
            return effect;
        }
    }

    private static class SingletonHolder {
        private static final ProjectileAmountStat INSTANCE = new ProjectileAmountStat();
    }
}

