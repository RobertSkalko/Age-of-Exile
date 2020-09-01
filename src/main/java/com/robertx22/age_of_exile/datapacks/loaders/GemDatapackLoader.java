package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class GemDatapackLoader extends BaseDataPackLoader<Gem> {
    static String ID = "gems";

    public GemDatapackLoader() {
        super(SlashRegistryType.GEM, ID, x -> Gem.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Gem>(SlashRegistry.Gems()
            .getSerializable(), ID);
    }

}
