package com.robertx22.age_of_exile.database.data.gear_types.bases;

import com.robertx22.age_of_exile.database.data.level_ranges.LevelRange;

public abstract class BaseOffHand extends BaseGearType {

    public BaseOffHand(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public final int Weight() {
        return super.Weight() / 2;
    }

}
