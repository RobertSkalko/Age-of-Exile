package com.robertx22.mine_and_slash.database.data.gearitemslots.bases;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;

public abstract class BaseCurio extends BaseGearType {

    public final int maximumRareAffixes(GearRarity rarity) {
        if (rarity.maxAffixes() > 2) {
            return rarity.maxAffixes() - 2;
        } else {
            return rarity.maxAffixes();
        }
    }

    @Override
    public int Weight() {
        return 750;
    }

}
