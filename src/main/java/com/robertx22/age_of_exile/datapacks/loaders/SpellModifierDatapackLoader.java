package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.spells.modifiers.SpellModifier;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class SpellModifierDatapackLoader extends BaseDataPackLoader<SpellModifier> {
    static String ID = "spell_modifiers";

    public SpellModifierDatapackLoader() {
        super(SlashRegistryType.SPELL_MODIFIER, ID, x -> SpellModifier.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<SpellModifier>(SlashRegistry.SpellModifiers()
            .getSerializable(), ID);
    }
}
