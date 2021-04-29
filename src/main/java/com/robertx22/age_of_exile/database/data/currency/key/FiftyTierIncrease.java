package com.robertx22.age_of_exile.database.data.currency.key;

public class FiftyTierIncrease extends IncreaseDungeonKeyTier {

    public FiftyTierIncrease() {
        super();
    }

    @Override
    public String GUID() {
        return "currency/fifty_tier";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Large Dungeon Key Upgrade";
    }

    @Override
    public int increaseTierBy() {
        return 50;
    }
}

