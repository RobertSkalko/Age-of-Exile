package com.robertx22.mine_and_slash.datapacks.loaders;

import com.robertx22.exiled_lib.registry.SlashRegistry;
import com.robertx22.exiled_lib.registry.SlashRegistryType;
import com.robertx22.mine_and_slash.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.mine_and_slash.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.mine_and_slash.datapacks.seriazables.SerializableBaseGearType;

public class BaseGearTypeDatapackLoader extends BaseDataPackLoader<BaseGearType> {
    static String ID = "base_gear_types";

    public BaseGearTypeDatapackLoader() {
        super(SlashRegistryType.GEAR_TYPE, ID, x -> SerializableBaseGearType.EMPTY
            .fromJson(x));
    }

    @Override
    public SlashDatapackGenerator getDataPackGenerator() {
        return new SlashDatapackGenerator<BaseGearType>(SlashRegistry.GearTypes()
            .getSerializable(), ID);
    }
}
