package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.database.registry.empty_entries.EmptyUniqueGear;
import com.robertx22.age_of_exile.database.data.unique_items.IUnique;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;

public class UniqueGearDatapackLoader extends BaseDataPackLoader<IUnique> {
    static String ID = "unique_gears";

    public UniqueGearDatapackLoader() {
        super(SlashRegistryType.UNIQUE_GEAR, ID, x -> new EmptyUniqueGear()
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<IUnique>(SlashRegistry.UniqueGears()
            .getSerializable(), ID);
    }
}
