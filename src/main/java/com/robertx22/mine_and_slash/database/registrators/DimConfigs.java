package com.robertx22.mine_and_slash.database.registrators;

import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.exiled_lib.registry.ISlashRegistryInit;

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
