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
        common.reforge_stat_percent = 20;
        common.higher_rar = IRarity.UNCOMMON;

        common.setCommonFields();
        common.addToSerializables();

        GearRarity uncommon = new GearRarity();
        uncommon.affixes = 1;
        uncommon.weight = 2000;
        uncommon.item_tier = 1;
        uncommon.item_model_data_num = 2;
        uncommon.item_tier_power = 1.25F;
        uncommon.dust_per_sal = new MinMax(1, 2);
        uncommon.item_value_multi = 1.25F;
        uncommon.higher_rar = IRarity.RARE_ID;
        uncommon.rar_ess_per_sal = 1;
        uncommon.unbreaking_chance = 10;
        uncommon.reforge_stat_percent = 40;
        uncommon.setUncommonFields();
        uncommon.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.dust_per_sal = new MinMax(2, 2);
        rare.item_tier = 2;
        rare.item_model_data_num = 3;
        rare.affixes = 2;
        rare.weight = 300;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.higher_rar = IRarity.EPIC_ID;
        rare.rar_ess_per_sal = 1;
        rare.setRareFields();
        rare.unbreaking_chance = 25;
        rare.reforge_stat_percent = 60;
        rare.addToSerializables();

        GearRarity epic = new GearRarity();
        epic.affixes = 4;
        epic.weight = 100;
        epic.item_tier = 3;
        epic.item_model_data_num = 4;
        epic.dust_per_sal = new MinMax(2, 3);
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.rar_ess_per_sal = 0;
        epic.unbreaking_chance = 50;
        epic.reforge_stat_percent = 80;
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity legendary = new GearRarity();
        legendary.affixes = 5;
        legendary.weight = 20;
        legendary.item_tier = 4;
        legendary.item_model_data_num = 5;
        legendary.dust_per_sal = new MinMax(3, 5);
        legendary.item_tier_power = 2;
        legendary.item_value_multi = 2;
        legendary.rar_ess_per_sal = 0;
        legendary.unbreaking_chance = 70;
        legendary.reforge_stat_percent = 100;
        legendary.setLegendaryFIelds();
        legendary.addToSerializables();

        GearRarity unique = new GearRarity();
        unique.affixes = 0;
        unique.weight = 25;
        unique.item_tier_power = 2;
        unique.item_value_multi = 2;
        unique.item_tier = 5;
        unique.dust_per_sal = new MinMax(5, 5);
        unique.setUniqueFields();
        unique.addToSerializables();
        unique.unbreaking_chance = 50;
        unique.announce_in_chat = true;
        unique.is_unique_item = true;

        GearRarity runeword = new GearRarity();
        runeword.affixes = 0;
        runeword.weight = 0;
        runeword.item_tier_power = 2;
        runeword.item_value_multi = 2;
        runeword.item_tier = 5;
        runeword.dust_per_sal = new MinMax(5, 5);
        runeword.setRunewordFields();
        runeword.addToSerializables();
        runeword.unbreaking_chance = 50;
        runeword.is_unique_item = true;

        GearRarity runicSpell = new GearRarity();
        runicSpell.affixes = 0;
        runicSpell.weight = 0;
        runicSpell.item_tier_power = 2;
        runicSpell.item_value_multi = 2;
        runicSpell.item_tier = 5;
        runicSpell.dust_per_sal = new MinMax(5, 5);
        runicSpell.setRunicSpellFields();
        runicSpell.addToSerializables();
        runicSpell.unbreaking_chance = 50;
        runicSpell.is_unique_item = true;

    }
}
