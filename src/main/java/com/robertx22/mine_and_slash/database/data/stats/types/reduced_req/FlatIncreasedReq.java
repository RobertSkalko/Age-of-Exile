package com.robertx22.mine_and_slash.database.data.stats.types.reduced_req;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.Dexterity;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.BaseCoreStat;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

public class FlatIncreasedReq extends Stat {
    BaseCoreStat statReq;

    public FlatIncreasedReq(BaseCoreStat statReq) {
        this.statReq = statReq;
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public StatScaling getScaling() {
        return Dexterity.INSTANCE.getScaling();
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
