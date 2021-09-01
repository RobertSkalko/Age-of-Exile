package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class MultiplyNumberByPercentEffect extends StatEffect {

    String num_id = "";

    public MultiplyNumberByPercentEffect(String num) {
        super("multiply_" + num + "_num", "multiply_num");
        this.num_id = num;
    }

    MultiplyNumberByPercentEffect() {
        super("", "multiply_num");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(num_id).number += event.data.getNumber(num_id).number * data.getValue() / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return MultiplyNumberByPercentEffect.class;
    }
}

