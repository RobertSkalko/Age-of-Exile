package com.robertx22.age_of_exile.database.data.rarities.mobs;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseCommon;

public class CommonMob extends BaseCommon implements MobRarity {

    private CommonMob() {
    }

    public static CommonMob getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public float StatMultiplier() {
        return 0.9F;
    }

    @Override
    public int minMobLevelForRandomSpawns() {
        return 0;
    }

    @Override
    public float DamageMultiplier() {
        return 1F;
    }

    @Override
    public float ExtraHealthMulti() {
        return 0.3F;
    }

    @Override
    public String locNameForLangFile() {
        return "Weak";
    }

    @Override
    public float LootMultiplier() {
        return 0.7F;
    }

    @Override
    public float oneAffixChance() {
        return 0;
    }

    @Override
    public float bothAffixesChance() {
        return 0;
    }

    @Override
    public float expMulti() {
        return 1;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    private static class SingletonHolder {
        private static final CommonMob INSTANCE = new CommonMob();
    }
}
