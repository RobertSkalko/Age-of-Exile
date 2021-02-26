package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.item.Items;

public class FarmingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(2, PlayerSkillEnum.FARMING);
        b.addDefaultBonusExpRewards();

        DropRewardsBuilder skillDrops = DropRewardsBuilder.of(2);

        DropRewardsBuilder rareDrops = DropRewardsBuilder.of(0.25F);
        rareDrops.dropReward(new SkillDropReward(10, 10, Items.BONE_MEAL, new MinMax(1, 2)));
        rareDrops.dropReward(new SkillDropReward(20, 2, Items.GOLDEN_APPLE, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(30, 1, Items.EMERALD, new MinMax(1, 3)));
        rareDrops.dropReward(new SkillDropReward(40, 2, Items.EMERALD, new MinMax(2, 5)));
        rareDrops.dropReward(new SkillDropReward(50, 1, Items.ENCHANTED_GOLDEN_APPLE, new MinMax(1, 1)));

        b.skill.dropTables.add(skillDrops.build());
        b.skill.dropTables.add(rareDrops.build());

        b.blockExp(ModRegistry.BLOCKS.PLANT1, 10);
        b.blockExp(ModRegistry.BLOCKS.PLANT2, 10);

        b.regens(10, 2);
        b.regens(20, 4);
        b.regens(30, 6);
        b.regens(40, 8);
        b.regens(50, 10);

        return b.build();
    }
}
