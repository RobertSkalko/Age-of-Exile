package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public class IsBooleanTrueCondition extends StatCondition<EffectData> {

    String booleanId = "";

    public IsBooleanTrueCondition(String booleanId) {
        super("is_bool_true");
        this.booleanId = booleanId;
    }

    private IsBooleanTrueCondition() {
        super("is_bool_true");
    }

    @Override
    public boolean can(EffectData event, StatData data, Stat stat) {
        return event.data.getBoolean(booleanId);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsBooleanTrueCondition.class;
    }

}
