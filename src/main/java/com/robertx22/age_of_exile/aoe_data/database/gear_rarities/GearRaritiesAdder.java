package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

public class GearRaritiesAdder implements ExileRegistryInit {

    @Override
    public void registerAll() {

        GearRarity common = new GearRarity();
        common.affixes = 0;
        common.base_stat_percents = new MinMax(0, 75);
        common.weight = 5000;
        common.item_tier_power = 1;
        common.item_tier = 0;
        common.item_value_multi = 1;
        common.item_model_data_num = 1;
        common.dust_per_sal = new MinMax(1, 1);
        common.higher_rar = IRarity.UNCOMMON;

        common.setCommonFields();
        common.addToSerializables();

        GearRarity uncommon = new GearRarity();
        uncommon.bonus_effective_lvls = 2;
        uncommon.affixes = 1;
        uncommon.weight = 2000;
        uncommon.item_tier = 1;
        uncommon.item_model_data_num = 2;
        uncommon.item_tier_power = 1.25F;
        uncommon.dust_per_sal = new MinMax(1, 2);
        uncommon.item_value_multi = 1.25F;
        uncommon.higher_rar = IRarity.RARE_ID;
        uncommon.upgrades = new MinMax(1, 2);
        uncommon.rar_ess_per_sal = 1;
        uncommon.upgrade_lvl_to_increase_rar = 4;
        uncommon.unbreaking_chance = 10;
        uncommon.setUncommonFields();
        uncommon.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.bonus_effective_lvls = 5;
        rare.dust_per_sal = new MinMax(2, 2);
        rare.item_tier = 2;
        rare.item_model_data_num = 3;
        rare.affixes = 2;
        rare.weight = 300;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.higher_rar = IRarity.EPIC_ID;
        rare.rar_ess_per_sal = 1;
        rare.upgrade_lvl_to_increase_rar = 8;
        rare.setRareFields();
        rare.unbreaking_chance = 25;
        rare.upgrades = new MinMax(1, 3);
        rare.addToSerializables();

        GearRarity epic = new GearRarity();
        epic.bonus_effective_lvls = 10;
        epic.affixes = 3;
        epic.weight = 100;
        epic.item_tier = 3;
        epic.item_model_data_num = 4;
        epic.dust_per_sal = new MinMax(2, 3);
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.rar_ess_per_sal = 0;
        epic.unbreaking_chance = 50;
        epic.upgrades = new MinMax(3, 5);
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity unique = new GearRarity();
        unique.bonus_effective_lvls = 10;
        unique.affixes = 0;
        unique.weight = 25;
        unique.item_tier_power = 2;
        unique.item_value_multi = 2;
        unique.sockets = 0;
        unique.item_tier = 5;
        unique.dust_per_sal = new MinMax(5, 5);
        unique.setUniqueFields();
        unique.addToSerializables();
        unique.unbreaking_chance = 50;
        unique.upgrades = new MinMax(4, 5);
        unique.announce_in_chat = true;
        unique.is_unique_item = true;

        GearRarity runeword = new GearRarity();
        runeword.bonus_effective_lvls = 10;
        runeword.affixes = 0;
        runeword.sockets = 0;
        runeword.weight = 0;
        runeword.item_tier_power = 2;
        runeword.item_value_multi = 2;
        runeword.item_tier = 5;
        runeword.dust_per_sal = new MinMax(5, 5);
        runeword.setRunewordFields();
        runeword.addToSerializables();
        runeword.unbreaking_chance = 50;
        runeword.upgrades = new MinMax(4, 5);
        runeword.announce_in_chat = true;
        runeword.is_unique_item = true;

    }
}
