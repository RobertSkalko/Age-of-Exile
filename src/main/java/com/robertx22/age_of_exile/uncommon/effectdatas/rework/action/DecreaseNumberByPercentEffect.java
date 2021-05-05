package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class DecreaseNumberByPercentEffect extends StatEffect {

    String num_id = "";

    public DecreaseNumberByPercentEffect(String num) {
        super("decrease_" + num + "_num", "decrease_num");
        this.num_id = num;
    }

    DecreaseNumberByPercentEffect() {
        super("", "decrease_num");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(num_id).number -= event.data.getOriginalNumber(num_id).number * data.getAverageValue() / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return DecreaseNumberByPercentEffect.class;
    }
}