package com.robertx22.mine_and_slash.database.data.stats.effects.spell_calc;

import com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpellStatsCalcEffect;

public class ReduceManaCostEffect extends BaseSpellCalcEffect {

    @Override
    public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {

        float multi = data.getReverseMultiplier();

        if (effect.configs.has(SC.MANA_COST)) {
            effect.configs.multiplyValueBy(SC.MANA_COST, multi);
        }

        return effect;
    }

}

