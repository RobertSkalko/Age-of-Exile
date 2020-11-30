package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.capability.entity.EntityCap.UnitData;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;

import java.util.Objects;

public class PlayerStatUtils {

    public static void AddPlayerBaseStats(UnitData data, Unit unit) {

        try {
            BaseStatsConfig stats = Database.BaseStats()
                .get(BaseStatsAdder.PLAYER);

            Objects.requireNonNull(stats);

            stats.applyStats(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
