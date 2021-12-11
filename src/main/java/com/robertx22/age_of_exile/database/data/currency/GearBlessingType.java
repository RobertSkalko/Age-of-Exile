package com.robertx22.age_of_exile.database.data.currency;

import com.robertx22.age_of_exile.uncommon.localization.Words;

public enum GearBlessingType {
    NONE(Words.WeaponCrafting),
    LUCK_BOOST(null),
    BIG_LUCK_BOOST(null),
    DESTROY_PROTECT(Words.DestroyProtect),
    UP_WIPE_PROTECT(Words.WipeUpgradeProtect);

    public Words word;

    GearBlessingType(Words word) {
        this.word = word;
    }
}
