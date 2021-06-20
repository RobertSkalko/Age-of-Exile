package com.robertx22.age_of_exile.aoe_data.database.base_stats;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Agility;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Vitality;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Wisdom;
import com.robertx22.age_of_exile.database.registry.ExileRegistryInit;

public class BaseStatsAdder implements ExileRegistryInit {

    public static String PLAYER = "player";

    @Override
    public void registerAll() {
        playerStats().addToSerializables();
    }

    public static BaseStatsConfig playerStats() {

        BaseStatsConfig c = new BaseStatsConfig();

        c.id = PLAYER;

        c.nonScaled(Vitality.INSTANCE, 10);
        c.nonScaled(Wisdom.INSTANCE, 10);
        c.nonScaled(Agility.INSTANCE, 10);

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
