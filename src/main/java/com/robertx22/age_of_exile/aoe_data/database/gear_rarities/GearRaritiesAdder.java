package com.robertx22.age_of_exile.aoe_data.database.gear_rarities;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;

public class GearRaritiesAdder implements ISlashRegistryInit {
    @Override
    public void registerAll() {

        GearRarity common = new GearRarity();
        common.rank = IRarity.Common;
        common.unidentified_chance = 0;
        common.stat_req_multi = 0.5F;
        common.spawn_durability_hit = new MinMax(60, 80);
        common.affixes = new GearRarity.Part(0, 0, 0);
        common.sockets = new GearRarity.Part(0, 3, 15);
        common.weight = 5000;
        common.item_tier_power = 1;
        common.item_value_multi = 1;
        common.setCommonFields();
        common.addToSerializables();

        GearRarity magical = new GearRarity();
        magical.unidentified_chance = 0;
        magical.stat_req_multi = 0.75F;
        magical.spawn_durability_hit = new MinMax(60, 80);
        magical.affixes = new GearRarity.Part(1, 2, 15);
        magical.sockets = new GearRarity.Part(0, 2, 15);
        magical.weight = 2500;
        magical.item_tier_power = 1.25F;
        magical.item_value_multi = 1.25F;
        magical.setMagicalFields();
        magical.addToSerializables();

        GearRarity rare = new GearRarity();
        rare.unidentified_chance = 20;
        rare.stat_req_multi = 1;
        rare.spawn_durability_hit = new MinMax(60, 80);
        rare.affixes = new GearRarity.Part(3, 6, 15);
        rare.sockets = new GearRarity.Part(0, 0, 0);
        rare.weight = 200;
        rare.item_tier_power = 1.5F;
        rare.item_value_multi = 1.5F;
        rare.setRareFields();
        rare.addToSerializables();

        GearRarity relic = new GearRarity();
        relic.unidentified_chance = 20;
        relic.stat_req_multi = 1;
        relic.spawn_durability_hit = new MinMax(60, 80);
        relic.affixes = new GearRarity.Part(0, 0, 0);
        relic.sockets = new GearRarity.Part(3, 5, 20);
        relic.weight = 200;
        relic.item_tier_power = 1.5F;
        relic.item_value_multi = 1.5F;
        relic.setRelicFields();
        relic.addToSerializables();

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
