package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class PerkDatapackLoader extends BaseDataPackLoader<Perk> {
    static String ID = "perk";

    public PerkDatapackLoader() {
        super(SlashRegistryType.PERK, ID, x -> Perk.SERIALIZER
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<Perk>(SlashRegistry.Perks()
            .getSerializable(), ID);
    }
}

