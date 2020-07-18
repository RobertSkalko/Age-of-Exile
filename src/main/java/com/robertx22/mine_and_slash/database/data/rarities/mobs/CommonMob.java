package com.robertx22.mine_and_slash.database.data.rarities.mobs;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseCommon;

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
    public float DamageMultiplier() {
        return 1F;
    }

    @Override
    public float HealthMultiplier() {
        return 0.9F;
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
        return ModConfig.INSTANCE.RarityWeightConfig.MOBS.COMMON_WEIGHT.get();
    }

    private static class SingletonHolder {
        private static final CommonMob INSTANCE = new CommonMob();
    }
}
