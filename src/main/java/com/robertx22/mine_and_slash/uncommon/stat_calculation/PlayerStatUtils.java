package com.robertx22.mine_and_slash.uncommon.stat_calculation;

import com.robertx22.mine_and_slash.capability.entity.EntityCap.UnitData;
import com.robertx22.mine_and_slash.config.base_player_stat.BasePlayerStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;

public class PlayerStatUtils {

    public static void AddPlayerBaseStats(UnitData data, Unit unit) {
        BasePlayerStatContainer.INSTANCE.applyStats(data);
    }

}
