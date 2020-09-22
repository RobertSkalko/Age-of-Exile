package com.robertx22.age_of_exile.database.data.stats.datapacks;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class DatapackStat extends Stat {
    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public String locDescForLangFile() {
        return null;
    }

    @Override
    public String locNameForLangFile() {
        return null;
    }

    @Override
    public String GUID() {
        return null;
    }
}
