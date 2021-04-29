package com.robertx22.age_of_exile.database.data.currency.key;

public class TenTierIncrease extends IncreaseDungeonKeyTier {

    public TenTierIncrease() {
        super();
    }

    @Override
    public String GUID() {
        return "currency/ten_tier";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Medium Dungeon Key Upgrade";
    }

    @Override
    public int increaseTierBy() {
        return 10;
    }
}

