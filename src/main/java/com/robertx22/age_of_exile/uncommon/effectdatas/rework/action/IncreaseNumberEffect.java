package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public class IncreaseNumberEffect extends StatEffect<EffectData> {

    public IncreaseNumberEffect() {
        super("increase_number");
    }

    @Override
    public void activate(EffectData event, StatData data, Stat stat) {
        event.increaseByPercent(data.getAverageValue());
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return IncreaseNumberEffect.class;
    }
}
