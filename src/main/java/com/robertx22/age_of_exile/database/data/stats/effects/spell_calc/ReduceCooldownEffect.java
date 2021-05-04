package com.robertx22.age_of_exile.database.data.stats.effects.spell_calc;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;

public class ReduceCooldownEffect extends BaseSpellCalcEffect {

    @Override
    public SpellStatsCalculationEvent activate(SpellStatsCalculationEvent effect, StatData data, Stat stat) {
        effect.spellConfig.add(SpellModEnum.COOLDOWN, -data.getAverageValue());
        return effect;
    }

}
