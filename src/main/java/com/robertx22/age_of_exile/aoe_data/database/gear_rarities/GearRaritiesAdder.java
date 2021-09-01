package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        GearRarity common = new GearRarity();
        common.unidentified_chance = 0;
        common.stat_req_multi = 0.25F;
        common.spawn_durability_hit = new MinMax(60, 80);

        common.affixes = new GearRarity.Part(0, 0, 0);
        common.base_stat_percents = new MinMax(0, 75);
        common.weight = 5000;
        common.item_tier_power = 1;
        common.item_tier = 0;
        common.item_value_multi = 1;
        common.item_model_data_num = 1;
        common.essence_per_sal = new MinMax(1, 1);
        common.higher_rar = IRarity.UNCOMMON;
        common.setCommonFields();
        common.addToSerializables();

        GearRarity uncommon = new GearRarity();
        uncommon.unidentified_chance = 0;
        uncommon.base_stat_percents = new MinMax(5, 80);
        uncommon.stat_req_multi = 0.4F;
        uncommon.default_stat_percents = new MinMax(0, 100);
        uncommon.affix_stat_percents = new MinMax(80, 100);
        uncommon.spawn_durability_hit = new MinMax(60, 80);
        uncommon.affixes = new GearRarity.Part(1, 2, 15);
        uncommon.weight = 2500;
        uncommon.item_tier = 1;
        uncommon.item_model_data_num = 2;
        uncommon.item_tier_power = 1.25F;
        uncommon.essence_per_sal = new MinMax(1, 2);
        uncommon.item_value_multi = 1.25F;
        uncommon.higher_rar = IRarity.RARE_ID;
        uncommon.setUncommonFields();
        uncommon.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.unidentified_chance = 10;
        rare.base_stat_percents = new MinMax(20, 85);
        rare.stat_req_multi = 0.5F;
        rare.default_stat_percents = new MinMax(5, 100);
        rare.affix_stat_percents = new MinMax(70, 100);
        rare.essence_per_sal = new MinMax(2, 2);
        rare.item_tier = 2;
        rare.item_model_data_num = 3;
        rare.affixes = new GearRarity.Part(2, 3, 15);
        rare.weight = 1500;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.higher_rar = IRarity.EPIC_ID;
        rare.setRareFields();
        rare.addToSerializables();

        GearRarity epic = new GearRarity();
        epic.unidentified_chance = 15;
        epic.stat_req_multi = 0.6F;
        epic.base_stat_percents = new MinMax(30, 90);
        epic.default_stat_percents = new MinMax(20, 100);
        epic.affix_stat_percents = new MinMax(20, 100);
        epic.affixes = new GearRarity.Part(3, 4, 15);
        epic.weight = 400;
        epic.item_tier = 3;
        epic.item_model_data_num = 4;
        epic.essence_per_sal = new MinMax(2, 3);
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.higher_rar = IRarity.LEGENDARY_ID;
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity legendary = new GearRarity();
        legendary.unidentified_chance = 25;
        legendary.base_stat_percents = new MinMax(40, 100);
        legendary.stat_req_multi = 0.8F;
        legendary.default_stat_percents = new MinMax(30, 100);
        legendary.affix_stat_percents = new MinMax(30, 100);
        legendary.affixes = new GearRarity.Part(4, 5, 15);
        legendary.weight = 125;
        legendary.item_tier = 4;
        legendary.item_model_data_num = 5;
        legendary.item_tier_power = 2F;
        legendary.essence_per_sal = new MinMax(3, 3);
        legendary.item_value_multi = 2F;
        legendary.higher_rar = IRarity.MYTHIC_ID;
        legendary.announce_in_chat = true;
        legendary.setLegendaryFields();
        legendary.addToSerializables();

        GearRarity mythic = new GearRarity();
        mythic.unidentified_chance = 50;
        mythic.stat_req_multi = 1F;
        mythic.base_stat_percents = new MinMax(50, 100);
        mythic.default_stat_percents = new MinMax(40, 100);
        mythic.affix_stat_percents = new MinMax(40, 100);
        mythic.affixes = new GearRarity.Part(5, 6, 15);
        mythic.weight = 25;
        mythic.item_tier = 5;
        mythic.item_model_data_num = 6;
        mythic.essence_per_sal = new MinMax(5, 5);
        mythic.item_tier_power = 2.5F;
        mythic.item_value_multi = 3F;
        mythic.announce_in_chat = true;
        mythic.setMythicFields();
        mythic.addToSerializables();

        GearRarity unique = new GearRarity();
        unique.unidentified_chance = 100;
        unique.stat_req_multi = 1;
        unique.spawn_durability_hit = new MinMax(60, 80);
        unique.affixes = new GearRarity.Part(0, 0, 0);
        unique.weight = 0;
        unique.item_tier_power = 2;
        unique.item_value_multi = 2;
        unique.item_tier = 5;
        unique.essence_per_sal = new MinMax(5, 5);
        unique.setUniqueFields();
        unique.addToSerializables();
        unique.special_spawn_chest_bonus_chance = 0.05F;
        unique.special_spawn_chance = 0.1F;
        unique.announce_in_chat = true;
        unique.is_unique_item = true;

    }
}
