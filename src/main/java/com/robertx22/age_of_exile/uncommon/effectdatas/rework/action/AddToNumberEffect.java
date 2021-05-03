package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.number_provider.NumberProvider;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class AddToNumberEffect extends StatEffect {

    public String number_id;
    NumberProvider num_provider;

    public AddToNumberEffect(String id, String number_id, NumberProvider num_provider) {
        super(id, "add_to_number");
        this.num_provider = num_provider;
        this.number_id = number_id;
    }

    public AddToNumberEffect() {
        super("increase_number", "add_to_number");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(number_id).number += num_provider.getValue(event, event.getSide(statSource), data);
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return AddToNumberEffect.class;
    }
}
