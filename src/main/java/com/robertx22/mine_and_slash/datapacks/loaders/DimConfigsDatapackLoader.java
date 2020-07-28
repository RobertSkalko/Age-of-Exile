package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.DimensionConfig;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

public class DimConfigsDatapackLoader extends BaseDataPackLoader<DimensionConfig> {
    static String ID = "dimension_config";

    public DimConfigsDatapackLoader() {
        super(SlashRegistryType.DIMENSION_CONFIGS, ID, x -> DimensionConfig.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<DimensionConfig>(SlashRegistry.DimensionConfigs()
            .getSerializable(), ID);
    }
}
