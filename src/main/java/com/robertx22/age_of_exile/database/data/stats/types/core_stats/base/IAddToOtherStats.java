package com.robertx22.age_of_exile.database.data.stats.types.core_stats.base;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.IGUID;

public interface IAddToOtherStats extends IGUID {

    void addToOtherStats(EntityCap.UnitData unit, float v1, float v2);

}
