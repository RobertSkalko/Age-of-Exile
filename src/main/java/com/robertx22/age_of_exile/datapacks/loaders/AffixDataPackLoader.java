package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyAffix;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class AffixDataPackLoader extends BaseDataPackLoader<Affix> {
    static String ID = "affixes";

    public AffixDataPackLoader() {
        super(SlashRegistryType.AFFIX, ID, x -> EmptyAffix.getInstance()
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Affix>(SlashRegistry.Affixes()
            .getSerializable(), ID);
    }

}
