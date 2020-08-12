package com.robertx22.age_of_exile.database.data.stats;

import com.robertx22.age_of_exile.database.data.IGUID;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;

public interface ILocalStat extends IGUID {

    public default boolean IsNativeToGearType(BaseGearType slot) {
        return slot.baseStats()
            .stream()
            .anyMatch(x -> x.stat.equals(this.GUID()));
    }

}
