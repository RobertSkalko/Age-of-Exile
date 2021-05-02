package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RandomUtils;

public class RandomRollCondition extends StatCondition {

    public RandomRollCondition() {
        super("random_roll", "random_roll");
    }

    @Override
    public boolean can(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        return RandomUtils.roll(data.getAverageValue());
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return RandomRollCondition.class;
    }

}
