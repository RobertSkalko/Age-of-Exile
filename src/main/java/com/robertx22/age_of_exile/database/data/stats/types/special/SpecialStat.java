package com.robertx22.age_of_exile.database.data.stats.types.special;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IStatEffect;

public class SpecialStat extends Stat {

    String id;
    String longName;
    IStatEffect effect;

    public SpecialStat(String id, String longName, IStatEffect effect) {
        this.id = id;
        this.longName = longName;
        this.effect = effect;
        this.isLongStat = true;

        this.registerToSlashRegistry();
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return longName;
    }

    @Override
    public String GUID() {
        return id;
    }
}
