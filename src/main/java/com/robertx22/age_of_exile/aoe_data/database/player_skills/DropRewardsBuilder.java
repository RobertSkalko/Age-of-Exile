package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropTable;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.FOOD_ITEMS;

public class DropRewardsBuilder {

    SkillDropTable table = new SkillDropTable();

    public static DropRewardsBuilder of(float chancePerExp) {
        DropRewardsBuilder b = new DropRewardsBuilder();
        b.table.loot_chance_per_action_exp = chancePerExp;
        return b;
    }

    public DropRewardsBuilder dropReward(SkillDropReward reward) {
        table.drop_rewards.add(reward);
        return this;
    }

    public DropRewardsBuilder addFoodDrops() {
        dropReward(new SkillDropReward(1, 100, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.SPIRITUAL), new MinMax(1, 3)));
        dropReward(new SkillDropReward(10, 75, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.CELESTIAL), new MinMax(1, 3)));
        dropReward(new SkillDropReward(20, 50, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.EMPYREAN), new MinMax(1, 3)));
        dropReward(new SkillDropReward(30, 25, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.ANGELIC), new MinMax(1, 3)));
        dropReward(new SkillDropReward(40, 10, FOOD_ITEMS.MAT_TIER_MAP.get(SkillItemTier.DIVINE), new MinMax(1, 3)));
        return this;
    }

    public SkillDropTable build() {
        return table;
    }
}
