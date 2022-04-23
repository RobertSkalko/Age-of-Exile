package com.robertx22.age_of_exile.database.all_keys.base;

import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.database.registry.ExileDB;

public class BaseGearKey extends DataKey<BaseGearType> {
    public BaseGearKey(String id) {
        super(id);
    }

    @Override
    public BaseGearType getFromRegistry() {
        return ExileDB.GearTypes()
            .get(id);
    }

    @Override
    public BaseGearType getFromDataGen() {
        return ExileDB.GearTypes()
            .getFromSerializables(id);
    }

    @Override
    public Class getDataClass() {
        return BaseGearType.class;
    }
}
