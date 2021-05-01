package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public class IsDayCondition extends StatCondition<EffectData> {

    public IsDayCondition() {
        super("is_day", "is_day");
    }

    @Override
    public boolean can(EffectData event, StatData data, Stat stat) {
        return event.source.world.isDay();
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsDayCondition.class;
    }

}
