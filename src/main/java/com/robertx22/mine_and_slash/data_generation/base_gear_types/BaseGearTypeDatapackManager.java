package com.robertx22.mine_and_slash.data_generation.base_gear_types;

import com.robertx22.mine_and_slash.data_generation.BaseDataPackManager;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.event_hooks.data_gen.providers.SlashDataProvider;
import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import net.minecraft.data.DataGenerator;

public class BaseGearTypeDatapackManager extends BaseDataPackManager<BaseGearType> {
    static String ID = "base_gear_types";

    public BaseGearTypeDatapackManager() {
        super(SlashRegistryType.GEAR_TYPE, ID, x -> SerializableBaseGearType.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDataProvider getDataPackCreator(DataGenerator gen) {
        return new SlashDataProvider<BaseGearType>(gen, SlashRegistry.GearTypes()
            .getSerializable(), ID);
    }
}
