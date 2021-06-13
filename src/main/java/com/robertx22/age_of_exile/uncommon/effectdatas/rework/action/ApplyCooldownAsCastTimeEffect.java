package com.robertx22.age_of_exile.uncommon.effectdatas.rework.action;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class ApplyCooldownAsCastTimeEffect extends StatEffect {

    public ApplyCooldownAsCastTimeEffect() {
        super("apply_cd_as_cast_time", "apply_cd_as_cast_time");
    }

    @Override
    public void activate(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        if (event.isSpell() && event.getSpell().config.apply_cast_speed_to_cd) {
            event.data.getNumber(EventData.COOLDOWN_TICKS).number -= event.data.getOriginalNumber(EventData.COOLDOWN_TICKS).number * data.getAverageValue() / 100F;
        }
    }

    @Override
    public Class<? extends StatEffect> getSerClass() {
        return ApplyCooldownAsCastTimeEffect.class;
    }
}