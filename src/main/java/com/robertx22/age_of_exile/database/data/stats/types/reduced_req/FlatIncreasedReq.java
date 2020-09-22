package com.robertx22.age_of_exile.database.data.stats.types.reduced_req;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class FlatIncreasedReq extends Stat {
    BaseCoreStat statReq;

    public FlatIncreasedReq(BaseCoreStat statReq) {
        this.statReq = statReq;
        this.scaling = Dexterity.INSTANCE.getScaling();
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    public float getModifiedRequirement(Stat stat, float req, ExactStatData data) {
        if (stat == statReq) {
            return req + data.getAverageValue();
        }
        return req;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "Alters stat requirements of this item.";
    }

    @Override
    public String locNameForLangFile() {
        return statReq.locNameForLangFile() + " Requirement";
    }

    @Override
    public String GUID() {
        return statReq.GUID() + "_flat_stat_req";
    }
}
