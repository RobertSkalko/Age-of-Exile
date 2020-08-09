package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;

public abstract class BaseCurio extends BaseGearType {

    public BaseCurio(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }

    @Override
    public int Weight() {
        return 500;
    }

}
