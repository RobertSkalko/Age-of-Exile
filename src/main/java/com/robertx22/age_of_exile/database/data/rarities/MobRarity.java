package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;

public interface MobRarity extends Rarity {

    public int minMobLevelForRandomSpawns();

    public float DamageMultiplier();

    public float ExtraHealthMulti();

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
