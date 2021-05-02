package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;

import java.util.ArrayList;
import java.util.List;

public class EitherIsTrueCondition extends StatCondition {

    List<String> ifs = new ArrayList<>();

    public EitherIsTrueCondition(List<String> conditions, String id) {
        super(id, "either_is_true");
        this.ifs = conditions;

    }

    EitherIsTrueCondition() {
        super("", "either_is_true");
    }

    @Override
    public boolean can(EffectData event, StatData data, Stat stat) {
        return ifs.stream()
            .anyMatch(x -> Database.StatConditions()
                .get(x)
                .can(event, data, stat));
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return EitherIsTrueCondition.class;
    }

}
