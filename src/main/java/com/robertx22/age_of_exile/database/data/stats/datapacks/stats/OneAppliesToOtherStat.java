package com.robertx22.age_of_exile.database.data.stats.datapacks.stats;

import com.robertx22.age_of_exile.capability.entity.EntityCap;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.datapacks.base.DatapackStat;
import com.robertx22.age_of_exile.database.data.stats.name_regex.StatNameRegex;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.interfaces.IAffectsStats;

public class OneAppliesToOtherStat extends DatapackStat implements IAffectsStats {

    public static String SER_ID = "one_to_other";

    String id = "";
    public String adder_stat;
    public String stat_to_add_to;

    transient String locname;
    transient String desc;

    public OneAppliesToOtherStat(String id, String adder_stat, String stat_to_add_to, String locname, String desc) {
        super(SER_ID);
        this.id = id;
        this.adder_stat = adder_stat;
        this.stat_to_add_to = stat_to_add_to;
        this.locname = locname;
        this.desc = desc;

        this.min_val = 0;
        this.scaling = StatScaling.NONE;
    }

    public OneAppliesToOtherStat(String id, String adder_stat, String stat_to_add_to) {
        super(SER_ID);
        this.id = id;
        this.stat_to_add_to = stat_to_add_to;
        this.adder_stat = adder_stat;

        this.min_val = 0;
        this.scaling = StatScaling.NONE;
    }

    @Override
    public void affectStats(EntityCap.UnitData data, StatData statData) {
        StatData add_to = data.getUnit()
            .getCreateStat(stat_to_add_to);
        StatData adder = data.getUnit()
            .peekAtStat(adder_stat);
        StatData thisstat = data.getUnit()
            .peekAtStat(this.GUID());

        float val = adder.getAverageValue() * thisstat.getMultiplier();

        add_to.addAlreadyScaledFlat(val);
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

    @Override
    public String GUID() {
        return id;
    }

}
