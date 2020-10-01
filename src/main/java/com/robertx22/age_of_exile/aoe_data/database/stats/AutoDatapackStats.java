package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.HashMap;

public class AutoDatapackStats implements ISlashRegistryInit {
    public static HashMap<String, DatapackStat> STATS_TO_ADD_TO_SERIALIZATION = new HashMap<>();

    @Override
    public void registerAll() {
        STATS_TO_ADD_TO_SERIALIZATION.values()
            .forEach(x -> x.addToSerializables());
    }
}
