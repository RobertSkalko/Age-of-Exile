package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.library_of_exile.registry.IGUID;

public interface IAffectsStats extends IGUID {
    void affectStats(EntityData data, InCalcStatData statData);

}
