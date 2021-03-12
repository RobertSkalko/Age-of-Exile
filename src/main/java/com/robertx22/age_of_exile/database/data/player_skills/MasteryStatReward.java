package com.robertx22.age_of_exile.database.data.player_skills;

import com.robertx22.age_of_exile.database.OptScaleExactStat;

import java.util.ArrayList;
import java.util.List;

public class MasteryStatReward {
    public List<OptScaleExactStat> stats = new ArrayList<>();
    public int every_x_lvls = 10;

    public MasteryStatReward(List<OptScaleExactStat> stats, int every_x_lvls) {
        this.stats = stats;
        this.every_x_lvls = every_x_lvls;
    }

    public List<SkillStatReward> getStatRewards() {
        List<SkillStatReward> list = new ArrayList<>();
        for (int i = 50; i < 999; i++) {
            if (i % 10 == 0) {
                SkillStatReward s = new SkillStatReward(i, stats);
                list.add(s);
            }
        }
        return list;

    }
}
