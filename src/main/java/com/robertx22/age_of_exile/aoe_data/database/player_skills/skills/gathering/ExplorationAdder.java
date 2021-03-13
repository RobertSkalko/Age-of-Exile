package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillStatReward;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusFavor;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.TINKERING;

public class ExplorationAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(4, PlayerSkillEnum.EXPLORATION);
        b.addBonusYieldMasteryLevelStats(PlayerSkillEnum.EXPLORATION);
        b.addDefaultBonusExpRewards();
        //b.skill.exp_per_action = 25; exp is based on favor gained

        b.stat(new SkillStatReward(5,
            new OptScaleExactStat(2, BonusFavor.getInstance()),
            new OptScaleExactStat(2, TreasureQuality.getInstance())));

        b.stat(new SkillStatReward(15,
            new OptScaleExactStat(2, BonusFavor.getInstance()),
            new OptScaleExactStat(2, TreasureQuality.getInstance())));

        b.stat(new SkillStatReward(25,
            new OptScaleExactStat(3, BonusFavor.getInstance()),
            new OptScaleExactStat(3, TreasureQuality.getInstance())));

        b.stat(new SkillStatReward(35,
            new OptScaleExactStat(4, BonusFavor.getInstance()),
            new OptScaleExactStat(4, TreasureQuality.getInstance())));

        b.stat(new SkillStatReward(45,
            new OptScaleExactStat(5, BonusFavor.getInstance()),
            new OptScaleExactStat(5, TreasureQuality.getInstance())));

        b.addTieredDrops(1, x -> TINKERING.LOCKED_CHEST_TIER_MAP.get(x));

        return b.build();
    }
}
