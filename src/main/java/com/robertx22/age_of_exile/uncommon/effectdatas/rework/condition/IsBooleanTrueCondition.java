package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

public class IsBooleanTrueCondition extends StatCondition {

    String bool_id = "";

    public IsBooleanTrueCondition(String bool_id) {
        super("is_" + bool_id + "_true", "is_bool_true");
        this.bool_id = bool_id;
    }

    IsBooleanTrueCondition() {
        super("", "is_bool_true");
    }

    @Override
    public boolean can(EffectData event, StatData data, Stat stat) {

        return event.data.getBoolean(bool_id);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsBooleanTrueCondition.class;
    }

}
