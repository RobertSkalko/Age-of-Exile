package com.robertx22.age_of_exile.database.data.races;

import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusSkillExp;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

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

    public static List<RaceLevelingPerk> profession(PlayerSkillEnum skill) {
        List<RaceLevelingPerk> list = new ArrayList<>();
        list.add(RaceLevelingPerk.of(25, new OptScaleExactStat(10, new BonusSkillExp(skill))));
        list.add(RaceLevelingPerk.of(40, new OptScaleExactStat(15, new BonusSkillExp(skill))));
        list.add(RaceLevelingPerk.of(50, new OptScaleExactStat(25, new BonusSkillExp(skill))));
        return list;
    }
}