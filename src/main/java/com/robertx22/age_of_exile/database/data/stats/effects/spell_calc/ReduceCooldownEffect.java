package com.robertx22.age_of_exile.database.data.stats.effects.spell_calc;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.configs.SC;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;

public class ReduceCooldownEffect extends BaseSpellCalcEffect {

    @Override
    public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {

        float cdrEfficiency = 1;

        if (effect.configs.has(SC.CDR_EFFICIENCY)) {
            cdrEfficiency = effect.configs.get(SC.CDR_EFFICIENCY)
                .get(effect.ctx.calcData);
        }

        float multi = 1 - (data.getAverageValue() * cdrEfficiency / 100);

        effect.configs.multiplyValueBy(SC.COOLDOWN_SECONDS, multi);
        effect.configs.multiplyValueBy(SC.COOLDOWN_TICKS, multi);

        return effect;
    }

}
