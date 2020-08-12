package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;

import java.util.List;

public class StatUtils {

    public static StatData turnIntoStatData(Stat stat, List<ExactStatData> stats) {

        StatData data = new StatData();

        stats.forEach(x -> {
            if (x.getStat()
                .GUID()
                .equals(stat.GUID())) {
                data.add(x, null);
            }
        });

        return data;
    }
}
