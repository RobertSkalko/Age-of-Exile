package com.robertx22.mine_and_slash.database.data.rarities.mobs;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseEpic;

public class EpicMob extends BaseEpic implements MobRarity {

    private EpicMob() {
    }

    public static EpicMob getInstance() {
        return SingletonHolder.INSTANCE;
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
    public float HealthMultiplier() {
        return 4;
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
        return ModConfig.INSTANCE.RarityWeightConfig.MOBS.EPIC_WEIGHT.get();
    }

    private static class SingletonHolder {
        private static final EpicMob INSTANCE = new EpicMob();
    }
}
