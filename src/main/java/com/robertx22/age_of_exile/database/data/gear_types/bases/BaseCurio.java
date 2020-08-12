package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;

public abstract class BaseCurio extends BaseGearType {

    public BaseCurio(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public int Weight() {
        return 500;
    }

}
