package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

import java.util.ArrayList;
import java.util.List;

public class EitherIsTrueCondition extends StatCondition {

    List<String> ifs = new ArrayList<>();

    public EitherIsTrueCondition(String id, List<String> conditions) {
        super(id, "either_is_true");
        this.ifs = conditions;

    }

    EitherIsTrueCondition() {
        super("", "either_is_true");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return ifs.stream()
            .anyMatch(x -> ExileDB.StatConditions()
                .get(x)
                .can(event, statSource, data, stat));
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return EitherIsTrueCondition.class;
    }

}
