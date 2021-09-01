package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ExileEffectUtils;

public class IncreaseNumberPerCurseOnTarget extends StatEffect {

    public IncreaseNumberPerCurseOnTarget() {
        super("increase_number_per_curse_on_target", "increase_number_per_curse_on_target");

    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {

        int curses = ExileEffectUtils.countEffectsWithTag(event.target, EffectTags.curse);

        event.data.getNumber(EventData.NUMBER).number += event.data.getNumber() * curses * data.getValue() / 100F;
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return IncreaseNumberByPercentEffect.class;
    }
}
