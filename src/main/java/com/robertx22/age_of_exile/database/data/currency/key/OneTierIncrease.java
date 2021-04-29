package com.robertx22.age_of_exile.database.data.currency.key;

public class OneTierIncrease extends IncreaseDungeonKeyTier {

    public OneTierIncrease() {
        super();
    }

    @Override
    public String GUID() {
        return "currency/one_tier";
    }

    @Override
    public int getWeight() {
        return 0;
    }

    @Override
    public String locNameForLangFile() {
        return "Small Dungeon Key Upgrade";
    }

    @Override
    public int increaseTierBy() {
        return 1;
    }
}
