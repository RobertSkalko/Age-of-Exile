package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyUniqueGear;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;

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
