package com.robertx22.age_of_exile.database.data.rarities.mobs;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseEpic;

public class EpicMob extends BaseEpic implements MobRarity {

    private EpicMob() {
    }

    public static EpicMob getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public int minMobLevel() {
        return 15;
    }

    @Override
    public float StatMultiplier() {
        return 3.2F;
    }

    @Override
    public float DamageMultiplier() {
        return 2.2F;
    }

    @Override
    public float ExtraHealthMulti() {
        return 1;
    }

    @Override
    public float LootMultiplier() {
        return 6F;
    }

    @Override
    public String locNameForLangFile() {
        return "Elite";
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
        return 3;
    }

    @Override
    public int Weight() {
        return 200;
    }

    private static class SingletonHolder {
        private static final EpicMob INSTANCE = new EpicMob();
    }
}
