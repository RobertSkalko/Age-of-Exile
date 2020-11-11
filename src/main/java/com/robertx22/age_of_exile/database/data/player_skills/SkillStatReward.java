package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillStatReward {

    public int lvl_req = 1;
    public List<OptScaleExactStat> stats = new ArrayList<>();

    public SkillStatReward(int lvl, List<OptScaleExactStat> stats) {
        this.lvl_req = lvl;
        this.stats = stats;
    }

    public SkillStatReward(int lvl, OptScaleExactStat... stats) {
        this.lvl_req = lvl;
        this.stats = Arrays.asList(stats);
    }
}
