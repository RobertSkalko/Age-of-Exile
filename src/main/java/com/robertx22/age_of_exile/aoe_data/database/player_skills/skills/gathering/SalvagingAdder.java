package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.MISC_ITEMS;

public class SalvagingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(-1, PlayerSkillEnum.SALVAGING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();
        b.skill.exp_per_action = 20;

        DropRewardsBuilder skillDrops = DropRewardsBuilder.of(0.5F);

        skillDrops.dropReward(new SkillDropReward(1, 200, MISC_ITEMS.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER0), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(10, 175, MISC_ITEMS.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER1), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(20, 150, MISC_ITEMS.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER2), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(30, 100, MISC_ITEMS.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER3), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(40, 50, MISC_ITEMS.SALVAGED_ESSENCE_MAP.get(SkillItemTier.TIER4), new MinMax(1, 3)));
        b.skill.dropTables.add(skillDrops.build());

        return b.build();
    }
}
