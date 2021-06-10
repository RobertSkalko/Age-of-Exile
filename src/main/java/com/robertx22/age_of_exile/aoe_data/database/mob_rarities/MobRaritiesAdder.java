package com.robertx22.age_of_exile.aoe_data.database.mob_rarities;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.registry.ISlashRegistryInit;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.util.Formatting;

public class MobRaritiesAdder implements ISlashRegistryInit {

    @Override
    public void registerAll() {

        MobRarity normal = new MobRarity();
        normal.affixes = 0;
        normal.stat_multi = 1;
        normal.min_lvl = 0;
        normal.dmg_multi = 1;
        normal.extra_hp_multi = 1F;
        normal.exp_multi = 1;
        normal.loot_multi = 1;
        normal.weight = 1000;
        normal.higher_rar = IRarity.MAGICAL_ID;
        normal.setCommonFields();
        normal.loc_name = "";
        normal.addToSerializables();

        MobRarity elite = new MobRarity();
        elite.name_add = "\u2605"; // star
        elite.affixes = 1;
        elite.stat_multi = 3;
        elite.min_lvl = 5;
        elite.dmg_multi = 2;
        elite.extra_hp_multi = 3;
        elite.exp_multi = 2.5F;
        elite.loot_multi = 2;
        elite.weight = 100;
        elite.higher_rar = IRarity.RARE_ID;
        elite.setMagicalFields();
        elite.addToSerializables();

        MobRarity champ = new MobRarity();
        champ.name_add = "\u2605"; // star
        champ.affixes = 2;
        champ.stat_multi = 3.5F;
        champ.min_lvl = 15;
        champ.dmg_multi = 2;
        champ.extra_hp_multi = 5f;
        champ.exp_multi = 4F;
        champ.loot_multi = 4F;
        champ.weight = 50;
        champ.higher_rar = IRarity.BOSS_ID;
        champ.loot_lvl_modifier = 1;
        champ.setRareFields();
        champ.text_format = Formatting.GOLD.name();
        champ.addToSerializables();

        MobRarity boss = new MobRarity();
        boss.name_add = "\u2620"; // skull
        boss.affixes = 2;
        boss.stat_multi = 3.75F;
        boss.min_lvl = 20;
        boss.dmg_multi = 2.4F;
        boss.extra_hp_multi = 10f;
        boss.exp_multi = 10F;
        boss.loot_multi = 6F;
        boss.weight = 2;
        boss.loot_lvl_modifier = 1;
        boss.setBossFields();
        boss.addToSerializables();

    }
}
