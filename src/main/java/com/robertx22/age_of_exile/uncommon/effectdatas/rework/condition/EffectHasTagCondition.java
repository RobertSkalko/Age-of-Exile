package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.exile_effects.EffectTags;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectEvent;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;

public class EffectHasTagCondition extends StatCondition {

    EffectTags tag;

    public EffectHasTagCondition(EffectTags tag) {
        super("effect_has_tag_" + tag, "effect_has_tag");
        this.tag = tag;
    }

    EffectHasTagCondition() {
        super("", "effect_has_tag");
    }

    @Override
    public boolean can(EffectEvent event, EffectSides statSource, StatData data, Stat stat) {
        if (event.data.hasExileEffect()) {
            return event.data.getExileEffect()
                .hasTag(tag);
        }
        return false;
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return EffectHasTagCondition.class;
    }

}
