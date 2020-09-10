package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class SpellDatapackLoader extends BaseDataPackLoader<Spell> {
    static String ID = "spells";

    public SpellDatapackLoader() {
        super(SlashRegistryType.SPELL, ID, x -> Spell.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Spell>(SlashRegistry.Spells()
            .getSerializable(), ID);
    }

}