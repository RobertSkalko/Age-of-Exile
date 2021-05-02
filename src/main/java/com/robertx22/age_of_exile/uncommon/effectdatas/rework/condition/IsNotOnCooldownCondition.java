package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsNotOnCooldownCondition extends StatCondition {

    String cd_id = "";

    public IsNotOnCooldownCondition(String cdid) {
        super("is_" + cdid + "_not_on_cd", "is_not_on_cd");
        this.cd_id = cdid;
    }

    IsNotOnCooldownCondition() {
        super("", "is_not_on_cd");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return Load.Unit(event.getSide(statSource))
            .getCooldowns()
            .isOnCooldown(cd_id);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsNotOnCooldownCondition.class;
    }

}
