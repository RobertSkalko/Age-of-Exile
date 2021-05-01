package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public class ElementMatchesStat extends StatCondition<EffectData> {

    public ElementMatchesStat() {
        super("ele_match_stat", "ele_match_stat");
    }

    @Override
    public boolean can(EffectData event, StatData data, Stat stat) {
        return event.data.getElement()
            .elementsMatch(stat.getElement());
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return ElementMatchesStat.class;
    }

}
