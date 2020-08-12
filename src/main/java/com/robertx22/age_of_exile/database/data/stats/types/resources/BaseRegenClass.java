package com.robertx22.age_of_exile.database.data.stats.types.resources;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public abstract class BaseRegenClass extends Stat {

    @Override
    public String locDescForLangFile() {
        return "Regen happens every few seconds but is also used for other stats or spells";
    }

    public BaseRegenClass() {

    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

}
