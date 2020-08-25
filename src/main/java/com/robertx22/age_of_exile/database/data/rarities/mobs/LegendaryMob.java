package com.robertx22.age_of_exile.database.data.rarities.mobs;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseLegendary;

public class LegendaryMob extends BaseLegendary implements MobRarity {

    private LegendaryMob() {
    }

    public static LegendaryMob getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int minMobLevel() {
        return 20;
    }

    @Override
    public float StatMultiplier() {
        return 4F;
    }

    @Override
    public float DamageMultiplier() {
        return 2.3F;
    }

    @Override
    public float ExtraHealthMulti() {
        return 4;
    }

    @Override
    public float LootMultiplier() {
        return 10;
    }

    @Override
    public String locNameForLangFile() {
        return "Champion";
    }

    @Override
    public float oneAffixChance() {
        return 100;
    }

    @Override
    public float bothAffixesChance() {
        return 75;
    }

    @Override
    public float expMulti() {
        return 5;
    }

    @Override
    public int Weight() {
        return 25;
    }

    private static class SingletonHolder {
        private static final LegendaryMob INSTANCE = new LegendaryMob();
    }
}
