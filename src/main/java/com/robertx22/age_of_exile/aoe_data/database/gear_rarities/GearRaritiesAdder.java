package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

public class GearRaritiesAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        GearRarity common = new GearRarity();
        common.unidentified_chance = 0;
        common.stat_req_multi = 0.25F;
        common.spawn_durability_hit = new MinMax(60, 80);

        common.affixes = new GearRarity.Part(0, 0, 0);
        common.base_stat_percents = new MinMax(0, 75);
        common.weight = 6000;
        common.item_tier_power = 1;
        common.item_tier = 0;
        common.item_value_multi = 1;
        common.essence_per_sal = new MinMax(1, 1);
        common.higher_rar = IRarity.MAGICAL_ID;
        common.setCommonFields();
        common.addToSerializables();

        GearRarity magical = new GearRarity();
        magical.unidentified_chance = 0;
        magical.base_stat_percents = new MinMax(5, 80);
        magical.stat_req_multi = 0.4F;
        magical.default_stat_percents = new MinMax(0, 100);
        magical.affix_stat_percents = new MinMax(80, 100);
        magical.spawn_durability_hit = new MinMax(60, 80);
        magical.affixes = new GearRarity.Part(1, 2, 15);
        magical.weight = 2500;
        magical.item_tier = 1;
        magical.item_tier_power = 1.25F;
        magical.essence_per_sal = new MinMax(1, 2);
        magical.item_value_multi = 1.25F;
        magical.higher_rar = IRarity.RARE_ID;
        magical.setMagicalFields();
        magical.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.unidentified_chance = 10;
        rare.base_stat_percents = new MinMax(20, 85);
        rare.stat_req_multi = 0.5F;
        rare.default_stat_percents = new MinMax(5, 100);
        rare.affix_stat_percents = new MinMax(70, 100);
        rare.essence_per_sal = new MinMax(2, 2);
        rare.item_tier = 2;
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
        legendary.weight = 100;
        legendary.item_tier = 4;
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
        mythic.weight = 20;
        mythic.item_tier = 5;
        mythic.essence_per_sal = new MinMax(5, 5);
        mythic.item_tier_power = 2.5F;
        mythic.item_value_multi = 3F;
        mythic.announce_in_chat = true;
        mythic.setMythicFields();
        mythic.addToSerializables();

        GearRarity mi = new GearRarity();
        mi.unidentified_chance = 100;
        mi.stat_req_multi = 1F;
        mi.default_stat_percents = new MinMax(20, 100);
        mi.affix_stat_percents = new MinMax(80, 100);
        mi.spawn_durability_hit = new MinMax(60, 80);
        mi.weight = 0;
        mi.affixes = new GearRarity.Part(1, 2, 50);
        mi.item_tier_power = 1.5F;
        mi.item_tier = 5;
        mi.essence_per_sal = new MinMax(2, 3);
        mi.item_value_multi = 1.5F;
        mi.special_spawn_chance = 3;
        mi.is_unique_item = true;
        mi.setMIFields();
        mi.addToSerializables();

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
        unique.special_spawn_chest_bonus_chance = 1;
        unique.special_spawn_chance = 0.5F;
        unique.announce_in_chat = true;
        unique.is_unique_item = true;

        GearRarity fabled = new GearRarity();
        fabled.drops_after_tier = 25;
        fabled.unidentified_chance = 100;
        fabled.stat_req_multi = 1;
        fabled.spawn_durability_hit = new MinMax(20, 50);
        fabled.affixes = new GearRarity.Part(0, 0, 0);
        fabled.weight = 0;
        fabled.item_tier_power = 2;
        fabled.item_value_multi = 3;
        fabled.item_tier = 6;
        fabled.essence_per_sal = new MinMax(10, 15);
        fabled.setFabledFields();
        fabled.addToSerializables();
        fabled.special_spawn_chance = 0.5F;
        fabled.announce_in_chat = true;
        fabled.is_unique_item = true;

    }
}
