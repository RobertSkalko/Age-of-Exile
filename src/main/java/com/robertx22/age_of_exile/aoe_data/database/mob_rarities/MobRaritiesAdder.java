package com.robertx22.age_of_exile.aoe_data.database.mob_rarities;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;

public class MobRaritiesAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        MobRarity common = new MobRarity();
        common.affix_chance = 5;
        common.stat_multi = 1;
        common.min_lvl = 0;
        common.dmg_multi = 1;
        common.extra_hp_multi = 0.8f;
        common.exp_multi = 1;
        common.loot_multi = 1;
        common.weight = 1000;
        common.setCommonFields();
        common.addToSerializables();

        MobRarity uncommon = new MobRarity();
        uncommon.affix_chance = 25;
        uncommon.stat_multi = 1.4F;
        uncommon.min_lvl = 5;
        uncommon.dmg_multi = 1.3F;
        uncommon.extra_hp_multi = 1F;
        uncommon.exp_multi = 1.2F;
        uncommon.loot_multi = 1.2F;
        uncommon.weight = 500;
        uncommon.setMagicalFields();
        uncommon.addToSerializables();

        MobRarity rare = new MobRarity();
        rare.affix_chance = 40;
        rare.stat_multi = 2F;
        rare.min_lvl = 10;
        rare.dmg_multi = 2F;
        rare.extra_hp_multi = 1.5f;
        rare.exp_multi = 2F;
        rare.loot_multi = 2F;
        rare.weight = 400;
        rare.setRareFields();
        rare.addToSerializables();

        MobRarity epic = new MobRarity();
        epic.affix_chance = 50;
        epic.stat_multi = 3;
        epic.min_lvl = 15;
        epic.dmg_multi = 2.2F;
        epic.extra_hp_multi = 2.5f;
        epic.exp_multi = 3F;
        epic.loot_multi = 3F;
        epic.weight = 250;
        epic.setEpicFields();
        epic.addToSerializables();

        MobRarity legendary = new MobRarity();
        legendary.affix_chance = 75;
        legendary.stat_multi = 3.5F;
        legendary.min_lvl = 20;
        legendary.dmg_multi = 2.3F;
        legendary.extra_hp_multi = 5f;
        legendary.exp_multi = 4F;
        legendary.loot_multi = 4F;
        legendary.weight = 100;
        legendary.setLegendaryFields();
        legendary.addToSerializables();

        MobRarity boss = new MobRarity();
        boss.name_add = "\u2620"; // skull
        boss.affix_chance = 100;
        boss.stat_multi = 3.75F;
        boss.min_lvl = 20;
        boss.dmg_multi = 2.4F;
        boss.extra_hp_multi = 10f;
        boss.exp_multi = 6F;
        boss.loot_multi = 6F;
        boss.weight = 10;
        boss.setBossFields();
        boss.addToSerializables();

    }
}
