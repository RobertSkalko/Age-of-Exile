package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface IRarity<R extends Rarity> {

    public int getRarityRank();

    public R getRarity();

    public default boolean isUnique() {
        return this.getRarityRank() == Unique;
    }

    int Common = 0;
    int Magical = 1;
    int Rare = 2;
    int Epic = 3;
    int Legendary = 4;
    int Relic = 5;

    int Unique = 10;

}
