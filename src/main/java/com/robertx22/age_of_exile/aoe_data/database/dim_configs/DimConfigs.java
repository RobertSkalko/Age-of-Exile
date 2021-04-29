package com.robertx22.age_of_exile.aoe_data.database.dim_configs;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.dimension.DimensionIds;

public class DimConfigs implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        DimensionConfig c = DimensionConfig.Overworld();

        c.addToSerializables();
        DimensionConfig.Nether()
            .addToSerializables();
        DimensionConfig.End()
            .addToSerializables();

        DimensionConfig cc = new DimensionConfig();
        cc.max_lvl = Integer.MAX_VALUE;
        cc.min_lvl = 1;
        cc.dimension_id = DimensionIds.DUNGEON_DIMENSION.toString();
        cc.addToSerializables();

        DimensionConfig.Hell("world_of_exile:hell1", 20, 30)
            .addToSerializables();
        DimensionConfig.Hell("world_of_exile:hell2", 30, 40)
            .addToSerializables();
        DimensionConfig.Hell("world_of_exile:hell3", 40, 50)
            .addToSerializables();

    }
}
