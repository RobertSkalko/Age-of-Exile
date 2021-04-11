package com.robertx22.age_of_exile.uncommon.interfaces.data_items;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface IRarity<R extends Rarity> {

    public String getRarityRank();

    public R getRarity();

    public default boolean isUnique() {
        return this.getRarityRank()
            .equals(UNIQUE_ID);
    }

    String COMMON_ID = "common";
    String MAGICAL_ID = "magical";
    String RARE_ID = "rare";
    String EPIC_ID = "epic";
    String LEGENDARY_ID = "legendary";
    String MYTHIC_ID = "mythic";

    String BOSS_ID = "boss";

    String UNIQUE_ID = "unique";

    String MONSTER_UNIQUE_ID = "monster_unique";

}
