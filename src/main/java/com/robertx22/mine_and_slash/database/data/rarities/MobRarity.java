package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.mine_and_slash.mmorpg.Ref;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.Rarity;

public interface MobRarity extends Rarity {

    public float DamageMultiplier();

    public float HealthMultiplier();

    public float StatMultiplier();

    public float LootMultiplier();

    public float expMulti();

    public float oneAffixChance();

    public float bothAffixesChance();

    @Override
    public default String locNameLangFileGUID() {
        return Ref.MODID + ".mob_rarity." + formattedGUID();
    }

}
