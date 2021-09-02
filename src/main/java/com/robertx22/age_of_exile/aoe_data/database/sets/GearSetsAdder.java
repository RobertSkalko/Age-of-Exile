package com.robertx22.age_of_exile.aoe_data.database.sets;

import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearSetsAdder implements ExileRegistryInit {

    public static String SEASONS_SET = "seasons_set";

    @Override
    public void registerAll() {

        GearSet.Builder.of(SEASONS_SET, "Enduring The Seasons")
            .stat(2,
                new OptScaleExactStat(2, DatapackStats.VIT, ModType.FLAT),
                new OptScaleExactStat(2, DatapackStats.WIS, ModType.FLAT))
            .build();

    }
}
