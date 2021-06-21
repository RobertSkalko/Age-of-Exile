package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class DoubleDamageAction extends StatEffect {

    public DoubleDamageAction() {
        super("double_dmg", "double_dmg");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        event.data.getNumber(EventData.NUMBER).number += event.data.getOriginalNumber(EventData.NUMBER).number * 100F / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return DoubleDamageAction.class;
    }
}

