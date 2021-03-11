package com.robertx22.age_of_exile.database.data.stats.types.professions.all;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class TripleDropChance extends Stat {

    public static String GUID = "triple_drop_chance";

    public static TripleDropChance getInstance() {
        return TripleDropChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    private TripleDropChance() {
        this.base_val = 0;
        this.min_val = 0;
        this.scaling = StatScaling.SLOW;
        this.statGroup = StatGroup.Misc;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Triple Drop Chance";
    }

    private static class SingletonHolder {
        private static final TripleDropChance INSTANCE = new TripleDropChance();
    }
}

