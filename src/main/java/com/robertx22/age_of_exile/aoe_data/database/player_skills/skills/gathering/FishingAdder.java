package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.INSCRIBING;

public class FishingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(3, PlayerSkillEnum.FISHING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();
        b.skill.exp_per_action = 25;

        DropRewardsBuilder skillDrops = DropRewardsBuilder.of(1.5F);
        //skillDrops.addFoodDrops();
        skillDrops.dropReward(new SkillDropReward(1, 200, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.SPIRITUAL), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(10, 150, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.CELESTIAL), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(20, 100, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.EMPYREAN), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(30, 75, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.ANGELIC), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(40, 50, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.DIVINE), new MinMax(1, 3)));
        b.skill.dropTables.add(skillDrops.build());

        return b.build();
    }
}
