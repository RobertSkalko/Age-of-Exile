package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsDayCondition extends StatCondition {

    public IsDayCondition() {
        super("is_day", "is_day");
    }

    @Override
    public boolean can(EffectData event, EffectSides statSource, StatData data, Stat stat) {
        return event.source.world.isDay();
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsDayCondition.class;
    }

}
