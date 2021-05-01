package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.DataStats;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class BaseStatsAdder implements ISlashRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(DataStats.CRIT_CHANCE.get(), 1);
        c.nonScaled(DataStats.CRIT_DAMAGE.get(), 0);
        c.nonScaled(DataStats.SPELL_CRIT_CHANCE.get(), 1);
        c.nonScaled(DataStats.SPELL_CRIT_DAMAGE.get(), 0);

        c.scaled(DataStats.SPELL_ACCURACY.get(), 10);
        c.scaled(DataStats.ACCURACY.get(), 20);

        return c;

    }

}
