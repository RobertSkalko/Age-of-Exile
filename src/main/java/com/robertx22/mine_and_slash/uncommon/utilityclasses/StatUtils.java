package com.robertx22.mine_and_slash.uncommon.utilityclasses;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;

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
