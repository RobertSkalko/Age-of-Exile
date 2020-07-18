package com.robertx22.mine_and_slash.database.data.rarities.mobs;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseRare;

public class RareMob extends BaseRare implements MobRarity {

    private RareMob() {
    }

    public static RareMob getInstance() {
        return SingletonHolder.INSTANCE;
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
    public float HealthMultiplier() {
        return 2F;
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
        return ModConfig.INSTANCE.RarityWeightConfig.MOBS.RARE_WEIGHT.get();
    }

    private static class SingletonHolder {
        private static final RareMob INSTANCE = new RareMob();
    }
}
