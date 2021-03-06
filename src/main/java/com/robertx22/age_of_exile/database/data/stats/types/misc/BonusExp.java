package com.robertx22.age_of_exile.database.data.stats.types.misc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class BonusExp extends Stat {

    private BonusExp() {
    }

    public static BonusExp getInstance() {
        return BonusExp.SingletonHolder.INSTANCE;
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
        return "Increases xp gain";
    }

    @Override
    public String GUID() {
        return "bonus_exp";
    }

    @Override
    public String locNameForLangFile() {
        return "Bonus Experience";
    }

    private static class SingletonHolder {
        private static final BonusExp INSTANCE = new BonusExp();
    }
}
