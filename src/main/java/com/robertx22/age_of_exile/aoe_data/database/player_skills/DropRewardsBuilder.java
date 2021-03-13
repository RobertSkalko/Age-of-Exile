package com.robertx22.age_of_exile.aoe_data.database.player_skills;

import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropTable;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;

public class DropRewardsBuilder {

    SkillDropTable table = new SkillDropTable();

    public static DropRewardsBuilder of(float chancePerExp, SkillItemTier tier) {
        DropRewardsBuilder b = new DropRewardsBuilder();
        b.table.loot_chance_per_action_exp = chancePerExp;
        b.table.tier = tier;
        return b;
    }

    public DropRewardsBuilder dropReward(SkillDropReward reward) {
        table.drop_rewards.add(reward);
        return this;
    }

    public SkillDropTable build() {
        return table;
    }
}
