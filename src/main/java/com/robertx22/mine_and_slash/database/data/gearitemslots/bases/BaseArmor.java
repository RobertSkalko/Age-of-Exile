package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;

public abstract class BaseArmor extends BaseGearType {

    public BaseArmor(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public final int Weight() {
        return super.Weight() / 3;
    }

}
