package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsTargetShieldedCondition extends StatCondition {

    public IsTargetShieldedCondition() {
        super("is_target_shielded", "is_target_shielded");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.targetData.getResources()
            .getShield() > 0;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsTargetShieldedCondition.class;
    }

}

