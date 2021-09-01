package com.robertx22.age_of_exile.database.data.races;

import com.robertx22.age_of_exile.database.OptScaleExactStat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RaceLevelingPerk {

    public int lvl_needed = 1;

    public List<OptScaleExactStat> stats = new ArrayList<>();

    public static RaceLevelingPerk of(int lvl, OptScaleExactStat... stats) {
        RaceLevelingPerk d = new RaceLevelingPerk();
        d.lvl_needed = lvl;
        d.stats.addAll(Arrays.asList(stats));
        return d;
    }

}