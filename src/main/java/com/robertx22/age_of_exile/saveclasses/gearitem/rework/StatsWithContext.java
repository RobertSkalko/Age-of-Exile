package com.robertx22.age_of_exile.saveclasses.gearitem.rework;

import com.robertx22.age_of_exile.saveclasses.ExactStatData;

import java.util.ArrayList;
import java.util.List;

public class StatsWithContext {

    public List<StatModifierInfo> list;

    public StatsWithContext(List<StatModifierInfo> list) {
        this.list = list;
    }

    public List<ExactStatData> getExactStats() {
        List<ExactStatData> exacts = new ArrayList<>();
        for (StatModifierInfo stat : list) {
            exacts.add(stat.exactStat);
        }
        return exacts;
    }

    public int getAveragePercent() {
        int perc = 0;
        int times = 0;

        for (StatModifierInfo stat : list) {
            perc += stat.percent.get();
            times++;
        }

        if (perc < 1) {
            return 0;
        }

        return perc / times;
    }
}
