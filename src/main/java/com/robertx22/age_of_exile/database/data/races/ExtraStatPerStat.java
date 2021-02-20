package com.robertx22.age_of_exile.database.data.races;

import com.robertx22.age_of_exile.database.OptScaleExactStat;

public class ExtraStatPerStat {

    public OptScaleExactStat stat_given;

    public String for_stat = "";

    public ExtraStatPerStat(OptScaleExactStat stat_given, String for_stat) {
        this.stat_given = stat_given;
        this.for_stat = for_stat;
    }

    ExtraStatPerStat() {
    }
}
