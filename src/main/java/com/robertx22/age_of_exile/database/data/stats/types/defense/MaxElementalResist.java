package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.Ref;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class MaxElementalResist extends ElementalStat {

    public MaxElementalResist(Elements element) {
        super(element);
        this.scaling = StatScaling.NONE;
        this.statGroup = StatGroup.ELEMENTAL;
    }

    @Override
    public String locDescForLangFile() {
        return "Modifies maximum resistance number.";
    }

    @Override
    public String locDescLangFileGUID() {
        return Ref.MODID + ".stat.max_res";
    }

    @Override
    public String GUID() {
        return "max_" + element.guidName + "_resist";
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new MaxElementalResist(element);
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Max " + element.dmgName + " Resist";
    }

}
