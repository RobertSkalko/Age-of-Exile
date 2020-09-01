package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.mob_affixes.base.MobAffix;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class RunewordDatapackLoader extends BaseDataPackLoader<RuneWord> {
    static String ID = "runewords";

    public RunewordDatapackLoader() {
        super(SlashRegistryType.RUNEWORD, ID, x -> RuneWord.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<MobAffix>(SlashRegistry.Runewords()
            .getSerializable(), ID);
    }
}
