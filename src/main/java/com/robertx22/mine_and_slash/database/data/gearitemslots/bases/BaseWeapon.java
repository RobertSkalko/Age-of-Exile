package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import com.robertx22.mine_and_slash.database.data.level_ranges.LevelRange;

public abstract class BaseWeapon extends BaseGearType {

    public BaseWeapon(String guid, LevelRange levelRange, String locname) {
        super(guid, levelRange, locname);
    }
}
