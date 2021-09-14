package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        GearRarity common = new GearRarity();
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
        uncommon.base_stat_percents = new MinMax(5, 80);
        uncommon.default_stat_percents = new MinMax(0, 100);
        uncommon.affix_stat_percents = new MinMax(80, 100);
        uncommon.bonus_effective_lvls = 2;
        uncommon.affixes = new GearRarity.Part(1, 2, 15);
        uncommon.weight = 2000;
        uncommon.item_tier = 1;
        uncommon.item_model_data_num = 2;
        uncommon.item_tier_power = 1.25F;
        uncommon.essence_per_sal = new MinMax(1, 2);
        uncommon.item_value_multi = 1.25F;
        uncommon.higher_rar = IRarity.RARE_ID;
        uncommon.setUncommonFields();
        uncommon.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.bonus_effective_lvls = 5;
        rare.base_stat_percents = new MinMax(20, 85);
        rare.default_stat_percents = new MinMax(5, 100);
        rare.affix_stat_percents = new MinMax(70, 100);
        rare.essence_per_sal = new MinMax(2, 2);
        rare.item_tier = 2;
        rare.item_model_data_num = 3;
        rare.affixes = new GearRarity.Part(2, 3, 15);
        rare.weight = 300;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.higher_rar = IRarity.EPIC_ID;
        rare.setRareFields();
        rare.addToSerializables();

        GearRarity epic = new GearRarity();
        epic.bonus_effective_lvls = 10;
        epic.base_stat_percents = new MinMax(30, 90);
        epic.default_stat_percents = new MinMax(20, 100);
        epic.affix_stat_percents = new MinMax(20, 100);
        epic.affixes = new GearRarity.Part(4, 6, 25);
        epic.weight = 100;
        epic.item_tier = 3;
        epic.item_model_data_num = 4;
        epic.essence_per_sal = new MinMax(2, 3);
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity unique = new GearRarity();
        unique.bonus_effective_lvls = 10;
        unique.affixes = new GearRarity.Part(0, 0, 0);
        unique.weight = 25;
        unique.item_tier_power = 2;
        unique.item_value_multi = 2;
        unique.item_tier = 5;
        unique.essence_per_sal = new MinMax(5, 5);
        unique.setUniqueFields();
        unique.addToSerializables();
        unique.announce_in_chat = true;
        unique.is_unique_item = true;

        GearRarity runeword = new GearRarity();
        runeword.bonus_effective_lvls = 10;
        runeword.affixes = new GearRarity.Part(0, 0, 0);
        runeword.weight = 0;
        runeword.item_tier_power = 2;
        runeword.item_value_multi = 2;
        runeword.item_tier = 5;
        runeword.essence_per_sal = new MinMax(5, 5);
        runeword.setRunewordFields();
        runeword.addToSerializables();
        runeword.announce_in_chat = true;
        runeword.is_unique_item = true;

    }
}
