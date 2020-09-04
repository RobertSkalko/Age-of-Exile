package com.robertx22.age_of_exile.database.data.rarities.mobs;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseUnique;

public class BossMob extends BaseUnique implements MobRarity {

    private BossMob() {
    }

    public static String SKULL = "\u2620";

    public static BossMob getInstance() {
        return BossMob.SingletonHolder.INSTANCE;
    }

    @Override
    public int minMobLevelForRandomSpawns() {
        return 40;
    }

    @Override
    public float StatMultiplier() {
        return 3.75F;
    }

    @Override
    public float DamageMultiplier() {
        return 2.4F;
    }

    @Override
    public float ExtraHealthMulti() {
        return 5;
    }

    @Override
    public float LootMultiplier() {
        return 12;
    }

    @Override
    public String locNameForLangFile() {
        return "Boss";
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
        return 6;
    }

    @Override
    public int Weight() {
        return 3;
    }

    private static class SingletonHolder {
        private static final BossMob INSTANCE = new BossMob();
    }
}
