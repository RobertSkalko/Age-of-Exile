package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IncreaseNumberByPercentEffect extends StatEffect {

    public IncreaseNumberByPercentEffect() {
        super("increase_number", "increase_number");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.increaseByPercent(data.getAverageValue());
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return IncreaseNumberByPercentEffect.class;
    }
}
