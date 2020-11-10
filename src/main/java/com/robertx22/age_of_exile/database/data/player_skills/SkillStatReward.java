package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;

import java.util.ArrayList;
import java.util.List;

public class SkillStatReward {

    List<OptScaleExactStat> stats = new ArrayList<>();

    public SkillStatReward(List<OptScaleExactStat> stats) {
        this.stats = stats;
    }

}
