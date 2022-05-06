package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.ArrayList;
import java.util.List;

public class StatsWithContext {

    public List<StatsWithContext> list;

    public StatsWithContext(List<StatsWithContext> list) {
        this.list = list;
    }

    public List<ExactStatData> getExactStats() {
        List<ExactStatData> exacts = new ArrayList<>();
        for (StatsWithContext stat : list) {
            exacts.addAll(stat.getExactStats());
        }
        return exacts;
    }

    public int getAveragePercent() {
        return
    }
}
