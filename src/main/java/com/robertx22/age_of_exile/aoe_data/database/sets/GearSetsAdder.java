package com.robertx22.age_of_exile.aoe_data.database.sets;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.set.GearSet;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearSetsAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {
        GearSet.Builder.of("empty", "empty")
            .stat(1, new OptScaleExactStat(0, Health.getInstance()))
            .build();
    }
}
