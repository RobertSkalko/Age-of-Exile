package com.robertx22.mine_and_slash.data_generation.unique_gears;

import com.robertx22.mine_and_slash.data_generation.BaseDataPackManager;
import com.robertx22.mine_and_slash.database.data.unique_items.IUnique;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashDataProvider;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.exiled_lib.registry.empty_entries.EmptyUniqueGear;
import net.minecraft.data.DataGenerator;

public class UniqueGearDatapackManager extends BaseDataPackManager<IUnique> {
    static String ID = "unique_gears";

    public UniqueGearDatapackManager() {
        super(SlashRegistryType.UNIQUE_GEAR, ID, x -> new EmptyUniqueGear()
            .fromJson(x));
    }

    @Override
    public SlashDataProvider getDataPackCreator(DataGenerator gen) {
        return new SlashDataProvider<IUnique>(gen, SlashRegistry.UniqueGears()
            .getSerializable(), ID);
    }
}
