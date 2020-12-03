package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import net.minecraft.entity.LivingEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerStatUtils {

    public static List<StatContext> AddPlayerBaseStats(LivingEntity en) {

        try {
            BaseStatsConfig stats = Database.BaseStats()
                .get(BaseStatsAdder.PLAYER);
            Objects.requireNonNull(stats);
            return stats.getStatAndContext(en);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList();

    }

}
