package com.robertx22.age_of_exile.aoe_data.database.stats;

import com.robertx22.age_of_exile.database.data.stats.datapacks.base.BaseDatapackStat;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

import java.util.HashSet;
import java.util.Set;

public class AutoDatapackStats implements ISlashRegistryInit {
    public static Set<BaseDatapackStat> STATS_TO_ADD_TO_SERIALIZATION = new HashSet<>();

    @Override
    public void registerAll() {
        STATS_TO_ADD_TO_SERIALIZATION
            .forEach(x -> x.addToSerializables());
    }
}
