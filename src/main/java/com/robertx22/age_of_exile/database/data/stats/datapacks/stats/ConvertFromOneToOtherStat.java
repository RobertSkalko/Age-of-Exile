package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStats;

public class ConvertFromOneToOtherStat extends DatapackStat implements IAffectsStats {

    public static String SER_ID = "convert_one_to_other";

    public String adder_stat;
    public String stat_to_add_to;

    transient String locname;
    transient String desc;

    public ConvertFromOneToOtherStat(String id, String adder_stat, String stat_to_add_to, String locname, String desc) {
        super(SER_ID);
        this.id = id;
        this.adder_stat = adder_stat;
        this.stat_to_add_to = stat_to_add_to;
        this.locname = locname;
        this.desc = desc;

        this.is_percent = true;
        this.min_val = 0;
        this.scaling = StatScaling.NONE;
    }

    public ConvertFromOneToOtherStat(String adder_stat, String stat_to_add_to) {
        super(SER_ID);
        this.stat_to_add_to = stat_to_add_to;
        this.adder_stat = adder_stat;

        this.is_percent = true;
        this.min_val = 0;
        this.scaling = StatScaling.NONE;
    }

    @Override
    public void affectStats(EntityCap.UnitData data, InCalcStatData statData) {

        InCalcStatData add_to = data.getUnit()
            .getStatInCalculation(stat_to_add_to);
        InCalcStatData adder = data.getUnit()
            .getStatInCalculation(adder_stat);
        InCalcStatData thisstat = data.getUnit()
            .getStatInCalculation(this.GUID());

        float multi = thisstat.getFlatAverage() / 100F;
        float val = adder.getFlatAverage() * multi;

        add_to.addAlreadyScaledFlat(val);

        adder.addAlreadyScaledFlat(-val);
    }

    @Override
    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    @Override
    public boolean IsPercent() {
        return true;
    }

    @Override
    public String locDescForLangFile() {
        return desc;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

}
