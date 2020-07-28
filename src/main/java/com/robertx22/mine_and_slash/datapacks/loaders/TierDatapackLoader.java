package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.mine_and_slash.database.registry.SlashRegistry;
import com.robertx22.mine_and_slash.database.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.tiers.base.Tier;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

public class TierDatapackLoader extends BaseDataPackLoader<Tier> {
    static String ID = "tier";

    public TierDatapackLoader() {
        super(SlashRegistryType.TIER, ID, x -> Tier.SERIALIZER.fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Tier>(SlashRegistry.Tiers()
            .getSerializable(), ID);
    }
}

