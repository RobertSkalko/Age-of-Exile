package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.database.data.tiers.base.Tier;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

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

