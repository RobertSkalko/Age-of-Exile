package com.robertx22.age_of_exile.uncommon.interfaces;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

public interface IAffectsStats extends IGUID {
    void affectStats(EntityCap.UnitData data, StatData statData);

}
