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

    MinMax uniqueStatPercents();

    int AffixChance();

    int maxAffixes();

    int minAffixes();

    float itemTierPower();

    default int maximumOfOneAffixType() {
        return maxAffixes() / 2;
    }

    default MinMax getStatPercentsFor(IGearPart.Part part) {

        if (part == IGearPart.Part.AFFIX) {
            return baseStatPercents();
        }
        if (part == IGearPart.Part.BASE_STATS) {
            return affixStatPercents();
        } else if (part == IGearPart.Part.UNIQUE_STATS) {
            return uniqueStatPercents();
        } else {
            return StatPercents();
        }
    }

}
