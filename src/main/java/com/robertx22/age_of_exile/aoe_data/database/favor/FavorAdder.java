package com.robertx22.age_of_exile.aoe_data.database.favor;

import com.robertx22.age_of_exile.database.data.favor.FavorRank;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.Formatting;

import java.util.Arrays;

public class FavorAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        FavorRank none = new FavorRank("none");
        none.min = 0;
        none.rank = 0;

        none.can_salvage_loot = false;
        none.excludedRarities = Arrays.asList(IRarity.MYTHIC_ID, IRarity.LEGENDARY_ID);
        none.drop_currency = false;
        none.drop_gems = false;
        none.drop_runes = false;
        none.drop_lvl_rewards = false;
        none.drop_unique_gears = false;
        none.exp_multi = 0.25F;
        none.favor_drain_per_item = 0;

        none.locname = "Empty";
        none.text_format = Formatting.RED.getName();

        none.addToSerializables();

        FavorRank low = new FavorRank("low");
        low.min = 250;
        low.rank = 1;

        low.locname = "Low";
        low.text_format = Formatting.YELLOW.getName();

        low.addToSerializables();

        FavorRank normal = new FavorRank("normal");
        normal.min = 1;
        normal.rank = 2;

        normal.locname = "Normal";
        normal.text_format = Formatting.GREEN.getName();

        normal.addToSerializables();

        FavorRank high = new FavorRank("high");
        high.min = 1000;
        high.rank = 3;
        high.extra_item_favor_cost = 1.2F;
        high.extra_items_per_boss = 1;
        high.extra_items_per_chest = 1;

        high.locname = "High";
        high.text_format = Formatting.AQUA.getName();

        high.addToSerializables();

        FavorRank veryhigh = new FavorRank("very_high");
        veryhigh.min = 2000;
        veryhigh.rank = 4;
        veryhigh.extra_item_favor_cost = 3;
        veryhigh.extra_items_per_boss = 2;
        veryhigh.extra_items_per_chest = 2;
        veryhigh.exp_multi = 1.1F;

        veryhigh.locname = "Very High";
        veryhigh.text_format = Formatting.BLUE.getName();

        veryhigh.addToSerializables();

        FavorRank favored = new FavorRank("favored");
        favored.min = 5000;
        favored.rank = 5;
        favored.extra_item_favor_cost = 5;
        favored.extra_items_per_boss = 3;
        favored.extra_items_per_chest = 3;
        favored.exp_multi = 1.25F;

        favored.locname = "Favored";
        favored.text_format = Formatting.LIGHT_PURPLE.getName();

        favored.addToSerializables();

    }
}
