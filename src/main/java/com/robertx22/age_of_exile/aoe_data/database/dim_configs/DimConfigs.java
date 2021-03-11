package com.robertx22.age_of_exile.aoe_data.database.dim_configs;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class DimConfigs implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        DimensionConfig c = DimensionConfig.Overworld();

        c.addToSerializables();
        DimensionConfig.Nether()
            .addToSerializables();
        DimensionConfig.End()
            .addToSerializables();

        DimensionConfig.Hell("world_of_exile:hell1", 20, 30)
            .addToSerializables();
        DimensionConfig.Hell("world_of_exile:hell2", 30, 40)
            .addToSerializables();
        DimensionConfig.Hell("world_of_exile:hell3", 40, 50)
            .addToSerializables();

    }
}
