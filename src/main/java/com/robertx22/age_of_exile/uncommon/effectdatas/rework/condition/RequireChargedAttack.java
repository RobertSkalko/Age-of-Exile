package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class RequireChargedAttack extends StatCondition {

    public RequireChargedAttack() {
        super("req_charged_atk", "req_charged_atk");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        if (event.data.isBasicAttack()) {
            float cool = event.data.getNumber(EventData.ATTACK_COOLDOWN).number;
            return cool > 0.8F;
        } else {
            return true;
        }
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return RequireChargedAttack.class;
    }

}

