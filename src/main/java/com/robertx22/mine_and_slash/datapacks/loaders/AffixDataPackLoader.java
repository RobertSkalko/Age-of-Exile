package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyAffix;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

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
