package com.robertx22.age_of_exile.uncommon.effectdatas.rework.condition;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.components.ElementEventComponent;

public class ElementMatchesStat extends StatCondition<ElementEventComponent> {

    public ElementMatchesStat() {
        super("ele_match_stat");
    }

    @Override
    public boolean can(ElementEventComponent event, StatData data, Stat stat) {
        return event.element.elementsMatch(stat.getElement());
    }

    @Override
    public Class<? extends StatCondition> getSerClass() {
        return ElementMatchesStat.class;
    }

}
