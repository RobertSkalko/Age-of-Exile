package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.SlashRegistryType;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPart;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface IGearRarity extends Rarity, SalvagableItem, IStatPercents {
    @Override
    public default SlashRegistryType getSlashRegistryType() {
        return SlashRegistryType.GEAR_RARITY;
    }

    MinMax SpawnDurabilityHit();

    float statReqMulti();

    MinMax affixStatPercents();

    float valueMulti();

    MinMax uniqueStatPercents();

    int AffixChance();

    int maxAffixes();

    int minAffixes();

    float itemTierPower();

    float unidentifiedChance();

    default int maximumOfOneAffixType() {
        return maxAffixes() / 2;
    }

    default MinMax getStatPercentsFor(IGearPart.Part part) {

        if (part == IGearPart.Part.AFFIX) {
            return affixStatPercents();
        } else if (part == IGearPart.Part.UNIQUE_STATS) {
            return uniqueStatPercents();
        } else {
            return StatPercents();
        }
    }

}
