package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.OptScaleExactStat;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.player_skills.SkillStatReward;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusFavor;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.TINKERING;

public class ExplorationAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(4, PlayerSkillEnum.EXPLORATION);
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

        DropRewardsBuilder skillDrops = DropRewardsBuilder.of(1);
        skillDrops.dropReward(new SkillDropReward(5, 1, TINKERING.LOCKED_CHEST_TIER_MAP.get(SkillItemTier.SPIRITUAL), new MinMax(1, 1)));
        skillDrops.dropReward(new SkillDropReward(15, 100, TINKERING.LOCKED_CHEST_TIER_MAP.get(SkillItemTier.CELESTIAL), new MinMax(1, 1)));
        skillDrops.dropReward(new SkillDropReward(25, 10000, TINKERING.LOCKED_CHEST_TIER_MAP.get(SkillItemTier.EMPYREAN), new MinMax(1, 1)));
        skillDrops.dropReward(new SkillDropReward(35, 1000000, TINKERING.LOCKED_CHEST_TIER_MAP.get(SkillItemTier.ANGELIC), new MinMax(1, 1)));
        skillDrops.dropReward(new SkillDropReward(45, 100000000, TINKERING.LOCKED_CHEST_TIER_MAP.get(SkillItemTier.DIVINE), new MinMax(1, 1)));
        b.skill.dropTables.add(skillDrops.build());

        return b.build();
    }
}
