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
        common.stat_req_multi = 0.5F;
        common.spawn_durability_hit = new MinMax(60, 80);
        common.affixes = new GearRarity.Part(0, 0, 0);
        common.sockets = new GearRarity.Part(0, 3, 15);
        common.weight = 6000;
        common.item_tier_power = 1;
        common.item_value_multi = 1;
        common.higher_rar = IRarity.MAGICAL_ID;
        common.setCommonFields();
        common.addToSerializables();

        GearRarity magical = new GearRarity();
        magical.unidentified_chance = 0;
        magical.stat_req_multi = 0.25F;
        magical.default_stat_percents = new MinMax(0, 100);
        magical.affix_stat_percents = new MinMax(80, 100);
        magical.spawn_durability_hit = new MinMax(60, 80);
        magical.affixes = new GearRarity.Part(1, 2, 15);
        magical.sockets = new GearRarity.Part(0, 2, 15);
        magical.weight = 2500;
        magical.item_tier_power = 1.25F;
        magical.item_value_multi = 1.25F;
        magical.higher_rar = IRarity.RARE_ID;
        magical.setMagicalFields();
        magical.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.unidentified_chance = 10;
        rare.stat_req_multi = 0.5F;
        rare.default_stat_percents = new MinMax(5, 100);
        rare.affix_stat_percents = new MinMax(70, 100);
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
        epic.default_stat_percents = new MinMax(20, 100);
        epic.affix_stat_percents = new MinMax(20, 100);
        epic.affixes = new GearRarity.Part(3, 4, 15);
        epic.weight = 400;
        epic.item_tier_power = 1.7F;
        epic.item_value_multi = 1.7F;
        epic.higher_rar = IRarity.LEGENDARY_ID;
        epic.setEpicFields();
        epic.addToSerializables();

        GearRarity legendary = new GearRarity();
        legendary.unidentified_chance = 25;
        legendary.stat_req_multi = 0.8F;
        legendary.default_stat_percents = new MinMax(30, 100);
        legendary.affix_stat_percents = new MinMax(30, 100);
        legendary.affixes = new GearRarity.Part(4, 5, 15);
        legendary.weight = 100;
        legendary.item_tier_power = 2F;
        legendary.item_value_multi = 2F;
        legendary.higher_rar = IRarity.MYTHIC_ID;
        legendary.announce_in_chat = true;
        legendary.setLegendaryFields();
        legendary.addToSerializables();

        GearRarity mythic = new GearRarity();
        mythic.unidentified_chance = 50;
        mythic.stat_req_multi = 1F;
        mythic.default_stat_percents = new MinMax(40, 100);
        mythic.affix_stat_percents = new MinMax(40, 100);
        mythic.affixes = new GearRarity.Part(5, 6, 15);
        mythic.weight = 20;
        mythic.item_tier_power = 2.5F;
        mythic.item_value_multi = 3F;
        mythic.announce_in_chat = true;
        mythic.setMythicFields();
        mythic.addToSerializables();

        GearRarity antique = new GearRarity();
        antique.unidentified_chance = 0;
        antique.stat_req_multi = 1;
        antique.spawn_durability_hit = new MinMax(60, 80);
        antique.affixes = new GearRarity.Part(0, 0, 0);
        antique.sockets = new GearRarity.Part(2, 3, 50);
        antique.weight = 500;
        antique.item_tier_power = 1.2F;
        antique.item_value_multi = 1.2F;
        antique.higher_rar = IRarity.RELIC_ID;
        antique.setAntiqueFields();
        antique.addToSerializables();

        GearRarity relic = new GearRarity();
        relic.unidentified_chance = 20;
        relic.stat_req_multi = 1;
        relic.spawn_durability_hit = new MinMax(60, 80);
        relic.affixes = new GearRarity.Part(0, 0, 0);
        relic.sockets = new GearRarity.Part(3, 5, 20);
        relic.weight = 100;
        relic.item_tier_power = 1.5F;
        relic.item_value_multi = 1.5F;
        relic.announce_in_chat = true;
        relic.setRelicFields();
        relic.addToSerializables();

        GearRarity mi = new GearRarity();
        mi.unidentified_chance = 0;
        mi.stat_req_multi = 1F;
        mi.default_stat_percents = new MinMax(0, 100);
        mi.affix_stat_percents = new MinMax(80, 100);
        mi.spawn_durability_hit = new MinMax(60, 80);
        mi.weight = 0;
        mi.affixes = new GearRarity.Part(1, 2, 50);
        mi.sockets = new GearRarity.Part(0, 0, 0);
        mi.item_tier_power = 1.5F;
        mi.item_value_multi = 1.5F;
        mi.setMIFields();
        mi.addToSerializables();

        GearRarity unique = new GearRarity();
        unique.unidentified_chance = 25;
        unique.stat_req_multi = 1;
        unique.spawn_durability_hit = new MinMax(60, 80);
        unique.affixes = new GearRarity.Part(0, 0, 0);
        unique.sockets = new GearRarity.Part(0, 0, 0);
        unique.weight = 0;
        unique.item_tier_power = 2;
        unique.item_value_multi = 2;
        unique.setUniqueFields();
        unique.addToSerializables();
        unique.announce_in_chat = true;

    }
}
