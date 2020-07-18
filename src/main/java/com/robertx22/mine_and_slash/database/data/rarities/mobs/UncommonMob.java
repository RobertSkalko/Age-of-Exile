package com.robertx22.mine_and_slash.database.data.rarities.mobs;

import com.robertx22.mine_and_slash.config.forge.ModConfig;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.rarities.base.BaseMagical;

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
    public float DamageMultiplier() {
        return 1.3F;
    }

    @Override
    public float HealthMultiplier() {
        return 1.35F;
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
        return ModConfig.INSTANCE.RarityWeightConfig.MOBS.UNCOMMON_WEIGHT.get();
    }

    private static class SingletonHolder {
        private static final UncommonMob INSTANCE = new UncommonMob();
    }
}
