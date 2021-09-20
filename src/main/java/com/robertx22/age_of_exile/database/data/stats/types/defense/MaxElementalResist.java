package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class MaxElementalResist extends ElementalStat {

    public MaxElementalResist(Elements element) {
        super(element);
        this.scaling = StatScaling.NONE;
        this.group = StatGroup.ELEMENTAL;
        this.is_perc = true;
    }

    @Override
    public String locDescForLangFile() {
        return "Modifies maximum resistance number.";
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat.max_res";
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
    public String locNameForLangFile() {
        return "Max " + element.dmgName + " Resist";
    }

}
