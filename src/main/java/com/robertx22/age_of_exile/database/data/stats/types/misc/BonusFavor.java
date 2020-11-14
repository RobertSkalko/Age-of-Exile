package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class BonusFavor extends Stat {

    private BonusFavor() {
    }

    public static BonusFavor getInstance() {
        return BonusFavor.SingletonHolder.INSTANCE;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases favor gain";
    }

    @Override
    public String GUID() {
        return "bonus_favor";
    }

    @Override
    public String locNameForLangFile() {
        return "Bonus Favor";
    }

    private static class SingletonHolder {
        private static final BonusFavor INSTANCE = new BonusFavor();
    }
}
