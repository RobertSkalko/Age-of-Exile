package com.robertx22.age_of_exile.aoe_data.database.dim_configs;

import com.robertx22.age_of_exile.database.data.DimensionConfig;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class DimConfigs implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        DimensionConfig.Overworld()
            .addToSerializables();
        DimensionConfig.Nether()
            .addToSerializables();
        DimensionConfig.End()
            .addToSerializables();

        DimensionConfig abyss = new DimensionConfig(1, "world_of_exile:abyss").setMobTier(3);
        abyss.scale_to_nearest_player = true;
        abyss.addToSerializables();

    }
}
