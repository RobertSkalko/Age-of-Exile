package com.robertx22.age_of_exile.database.data.rarities.mobs;

import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.rarities.base.BaseMagical;

public class UncommonMob extends BaseMagical implements MobRarity {

    private UncommonMob() {
    }

    public static UncommonMob getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public float StatMultiplier() {
        return 1.4F;
    }

    @Override
    public int minMobLevelForRandomSpawns() {
        return 5;
    }

    @Override
    public float DamageMultiplier() {
        return 1.3F;
    }

    @Override
    public float ExtraHealthMulti() {
        return 0.5F;
    }

    @Override
    public String locNameForLangFile() {
        return "Rare";
    }

    @Override
    public float LootMultiplier() {
        return 1.5F;
    }

    @Override
    public float oneAffixChance() {
        return 75;
    }

    @Override
    public float bothAffixesChance() {
        return 0;
    }

    @Override
    public float expMulti() {
        return 1.2F;
    }

    @Override
    public int Weight() {
        return 500;
    }

    private static class SingletonHolder {
        private static final UncommonMob INSTANCE = new UncommonMob();
    }
}
