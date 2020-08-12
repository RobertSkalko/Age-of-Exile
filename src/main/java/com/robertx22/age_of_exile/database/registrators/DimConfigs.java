package com.robertx22.age_of_exile.database.registrators;

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
    }
}
