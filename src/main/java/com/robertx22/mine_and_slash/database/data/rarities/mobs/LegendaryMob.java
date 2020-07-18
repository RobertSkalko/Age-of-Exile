package com.robertx22.mine_and_slash.database.data.rarities.mobs;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseLegendary;

public class LegendaryMob extends BaseLegendary implements MobRarity {

    private LegendaryMob() {
    }

    public static LegendaryMob getInstance() {
        return SingletonHolder.INSTANCE;
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
    public float HealthMultiplier() {
        return 6F;
    }

    @Override
    public float LootMultiplier() {
        return 13F;
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
        return ModConfig.INSTANCE.RarityWeightConfig.MOBS.LEGENDARY_WEIGHT.get();
    }

    private static class SingletonHolder {
        private static final LegendaryMob INSTANCE = new LegendaryMob();
    }
}
