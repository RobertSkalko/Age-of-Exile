package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class RuneDatapackLoader extends BaseDataPackLoader<Rune> {
    static String ID = "runes";

    public RuneDatapackLoader() {
        super(SlashRegistryType.RUNE, ID, x -> Rune.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Rune>(SlashRegistry.Runes()
            .getSerializable(), ID);
    }

}
