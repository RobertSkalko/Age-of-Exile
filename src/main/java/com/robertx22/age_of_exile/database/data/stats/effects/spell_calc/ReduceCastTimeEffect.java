package com.robertx22.age_of_exile.database.data.stats.effects.spell_calc;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;

public class ReduceCastTimeEffect extends BaseSpellCalcEffect {

    @Override
    public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {

        float multi = data.getReverseMultiplier();

        if (effect.configs.has(SC.CAST_TIME_TICKS)) {
            effect.configs.multiplyValueBy(SC.CAST_TIME_TICKS, multi);
        }

        return effect;
    }

}
