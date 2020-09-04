package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.spell_schools.SpellSchool;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class SpellSchoolDatapackLoader extends BaseDataPackLoader<SpellSchool> {
    static String ID = "spell_school";

    public SpellSchoolDatapackLoader() {
        super(SlashRegistryType.SPELL_SCHOOL, ID, x -> SpellSchool.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<SpellSchool>(SlashRegistry.SpellSchools()
            .getSerializable(), ID);
    }
}

