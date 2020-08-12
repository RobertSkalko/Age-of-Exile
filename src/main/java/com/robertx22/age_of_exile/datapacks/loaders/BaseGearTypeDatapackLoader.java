package com.robertx22.age_of_exile.datapacks.loaders;

import com.robertx22.age_of_exile.database.data.gearitemslots.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.SlashRegistry;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.datapacks.generators.SlashDatapackGenerator;
import com.robertx22.age_of_exile.datapacks.seriazables.SerializableBaseGearType;

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
