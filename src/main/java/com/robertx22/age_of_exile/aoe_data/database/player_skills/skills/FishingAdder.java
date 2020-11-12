package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills;

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
        b.addFoodDrops();
        b.skill.exp_per_action = 50;
        b.skill.loot_chance_per_action_exp *= 3;

        b.dropReward(new SkillDropReward(1, 100, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.SPIRITUAL), new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(10, 75, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.CELESTIAL), new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(20, 50, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.EMPYREAN), new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(30, 25, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.ANGELIC), new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(40, 10, INSCRIBING.INK_TIER_MAP.get(SkillItemTier.DIVINE), new MinMax(1, 3)));

        return b.build();
    }
}
