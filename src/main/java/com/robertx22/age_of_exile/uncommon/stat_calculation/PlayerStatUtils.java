package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.config.base_player_stat.BasePlayerStatContainer;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;

public class PlayerStatUtils {

    public static void AddPlayerBaseStats(UnitData data, Unit unit) {
        BasePlayerStatContainer.INSTANCE.applyStats(data);
    }

}
