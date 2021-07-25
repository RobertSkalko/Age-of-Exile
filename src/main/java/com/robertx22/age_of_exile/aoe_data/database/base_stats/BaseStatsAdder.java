package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class BaseStatsAdder implements ExileRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(DatapackStats.VIT, 10);
        c.nonScaled(DatapackStats.WIS, 10);
        c.nonScaled(DatapackStats.AGI, 10);

        c.nonScaled(Stats.CRIT_CHANCE.get(), 1);
        c.nonScaled(Stats.CRIT_DAMAGE.get(), 1);
        c.nonScaled(Stats.SPELL_CRIT_DAMAGE.get(), 1);
        c.nonScaled(Stats.HEAL_CRIT_DAMAGE.get(), 1);
        c.nonScaled(Stats.SPELL_CRIT_CHANCE.get(), 1);
        c.nonScaled(Stats.SPELL_CRIT_DAMAGE.get(), 0);

        c.scaled(Stats.SPELL_ACCURACY.get(), 10);
        c.scaled(Stats.ACCURACY.get(), 20);

        return c;

    }

}
