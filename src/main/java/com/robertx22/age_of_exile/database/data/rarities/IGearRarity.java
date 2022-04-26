package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public interface IGearRarity extends Rarity, SalvagableItem, IStatPercents {
    @Override
    public default ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_RARITY;
    }

    MinMax affixStatPercents();

    MinMax baseStatPercents();

    float valueMulti();

    float itemTierPower();

    int getAffixAmount();

    default int maximumOfOneAffixType() {
        return getAffixAmount() / 2;
    }

    // todo remove this
    default MinMax getStatPercentsFor(IGearPart.Part part) {

        if (part == IGearPart.Part.AFFIX) {
            return baseStatPercents();
        }
        if (part == IGearPart.Part.BASE_STATS) {
            return affixStatPercents();
        } else {
            return StatPercents();
        }
    }

}
