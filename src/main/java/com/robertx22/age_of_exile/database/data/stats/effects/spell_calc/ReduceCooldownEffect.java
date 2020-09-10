package com.robertx22.age_of_exile.database.data.stats.effects.spell_calc;

import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModEnum;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseSpellCalcEffect;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalcEffect;

public class ReduceCooldownEffect extends BaseSpellCalcEffect {

    @Override
    public SpellStatsCalcEffect activate(SpellStatsCalcEffect effect, StatData data, Stat stat) {
        effect.data.add(SpellModEnum.COOLDOWN, data);
        return effect;
    }

}
