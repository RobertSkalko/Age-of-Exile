package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IncreaseNumberByPercentEffect extends StatEffect {

    String num_id = "";

    public IncreaseNumberByPercentEffect(String num) {
        super("increase_" + num + "_num", "increase_number");
        this.num_id = num;
    }

    IncreaseNumberByPercentEffect() {
        super("", "increase_number");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(num_id).number += event.data.getOriginalNumber(num_id).number * data.getAverageValue() / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return IncreaseNumberByPercentEffect.class;
    }
}
