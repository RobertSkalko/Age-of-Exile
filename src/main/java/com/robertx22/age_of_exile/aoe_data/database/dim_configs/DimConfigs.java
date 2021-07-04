package com.robertx22.age_of_exile.aoe_data.database.dim_configs;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.dimension.DimensionIds;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class DimConfigs implements ExileRegistryInit {

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

        DimensionConfig rift = new DimensionConfig();
        rift.max_lvl = Integer.MAX_VALUE;
        rift.min_lvl = 1;
        rift.dimension_id = DimensionIds.RIFT_DIMENSION.toString();
        rift.addToSerializables();

        DimensionConfig.Hell("world_of_exile:hell1", 25, 50)
            .addToSerializables();
    }
}
