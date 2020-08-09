package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;
import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

public class LevelRangeDatapackLoader extends BaseDataPackLoader<LevelRange> {
    static String ID = "level_ranges";

    public LevelRangeDatapackLoader() {
        super(SlashRegistryType.LEVEl_RANGE, ID, x -> LevelRange.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<LevelRange>(SlashRegistry.LevelRanges()
            .getSerializable(), ID);
    }
}

