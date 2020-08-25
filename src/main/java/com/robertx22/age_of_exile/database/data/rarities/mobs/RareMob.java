package com.robertx22.age_of_exile.database.data.rarities.mobs;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseRare;

public class RareMob extends BaseRare implements MobRarity {

    private RareMob() {
    }

    public static RareMob getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int minMobLevel() {
        return 10;
    }

    @Override
    public float StatMultiplier() {
        return 2;
    }

    @Override
    public float DamageMultiplier() {
        return 2F;
    }

    @Override
    public float ExtraHealthMulti() {
        return 1;
    }

    @Override
    public String locNameForLangFile() {
        return "Veteran";
    }

    @Override
    public float LootMultiplier() {
        return 2;
    }

    @Override
    public float oneAffixChance() {
        return 100;
    }

    @Override
    public float bothAffixesChance() {
        return 50;
    }

    @Override
    public float expMulti() {
        return 2;
    }

    @Override
    public int Weight() {
        return 250;
    }

    private static class SingletonHolder {
        private static final RareMob INSTANCE = new RareMob();
    }
}
