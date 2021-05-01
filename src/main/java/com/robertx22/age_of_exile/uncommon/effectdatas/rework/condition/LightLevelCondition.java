package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.EffectData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.components.NumberComparison;

public class LightLevelCondition extends StatCondition<EffectData> {

    NumberComparison comparison;

    public LightLevelCondition(String id, NumberComparison comp) {
        super(id, "light_level");
        this.comparison = comp;
    }

    public LightLevelCondition() {
        super("", "light_level");
    }

    @Override
    public boolean can(EffectData event, StatData data, Stat stat) {
        int light = event.source.world.getLightLevel(event.source.getBlockPos());
        return comparison.is(light);
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return LightLevelCondition.class;
    }

}
