package com.robertx22.age_of_exile.aoe_data.database.favor;

import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

import java.util.Arrays;

public class FavorAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        FavorRank low = new FavorRank("low");
        low.min = 0;
        low.rank = 0;

        low.can_salvage_loot = false;
        low.excludedRarities = Arrays.asList(IRarity.MYTHIC_ID, IRarity.LEGENDARY_ID);
        low.drop_currency = false;
        low.drop_gems = false;
        low.drop_runes = false;
        low.drop_lvl_rewards = false;
        low.drop_unique_gears = false;
        low.drop_exp = false;

        low.favor_drain_per_item = 0;
        low.addToSerializables();

        FavorRank normal = new FavorRank("normal");
        normal.min = 1;
        normal.rank = 1;
        normal.addToSerializables();

        /*
        FavorRank high = new FavorRank();
        high.min = 1000;
        high.rank = 2;
        high.extra_favor_drain = 1;
        high.extra_gear_chance = 5;
        high.extra_currency_chance = 5;

        high.addToSerializables();

        FavorRank veryhigh = new FavorRank();
        veryhigh.min = 2500;
        veryhigh.rank = 2;
        veryhigh.extra_favor_drain = 2;
        veryhigh.extra_gear_chance = 10;
        veryhigh.extra_currency_chance = 10;

        veryhigh.addToSerializables();

         */

    }
}
