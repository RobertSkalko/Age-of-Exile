package com.robertx22.mine_and_slash.database.data.stats;

import com.robertx22.mine_and_slash.database.data.IGUID;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;

public interface ILocalStat extends IGUID {

    public default boolean IsNativeToGearType(BaseGearType slot) {
        return slot.baseStats()
            .stream()
            .anyMatch(x -> x.stat == this.GUID());
    }

}
