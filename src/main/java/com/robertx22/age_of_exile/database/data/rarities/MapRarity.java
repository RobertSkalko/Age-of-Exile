package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface MapRarity extends Rarity, SalvagableItem, IStatPercents {

    MinMax AffixAmount();

}
