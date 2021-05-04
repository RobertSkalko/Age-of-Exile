package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class IsSpellCondition extends StatCondition {

    public IsSpellCondition() {
        super("is_spell", "is_spell");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        return event.isSpell();
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return IsSpellCondition.class;
    }

}
