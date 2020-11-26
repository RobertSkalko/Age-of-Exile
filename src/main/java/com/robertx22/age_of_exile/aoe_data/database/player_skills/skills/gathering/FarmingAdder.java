package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.DropRewardsBuilder;
import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.player_skills.items.foods.SkillItemTier;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.ALCHEMY;

public class FarmingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(2, PlayerSkillEnum.FARMING);
        b.addDefaultBonusExpRewards();

        DropRewardsBuilder skillDrops = DropRewardsBuilder.of(2);
        skillDrops.addFoodDrops();

        skillDrops.dropReward(new SkillDropReward(1, 100, ALCHEMY.MAT_TIER_MAP.get(SkillItemTier.SPIRITUAL), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(10, 75, ALCHEMY.MAT_TIER_MAP.get(SkillItemTier.CELESTIAL), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(20, 50, ALCHEMY.MAT_TIER_MAP.get(SkillItemTier.EMPYREAN), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(30, 25, ALCHEMY.MAT_TIER_MAP.get(SkillItemTier.ANGELIC), new MinMax(1, 3)));
        skillDrops.dropReward(new SkillDropReward(40, 10, ALCHEMY.MAT_TIER_MAP.get(SkillItemTier.DIVINE), new MinMax(1, 3)));

        DropRewardsBuilder rareDrops = DropRewardsBuilder.of(0.25F);
        rareDrops.dropReward(new SkillDropReward(10, 10, Items.BONE_MEAL, new MinMax(1, 2)));
        rareDrops.dropReward(new SkillDropReward(20, 2, Items.GOLDEN_APPLE, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(30, 1, Items.EMERALD, new MinMax(1, 3)));
        rareDrops.dropReward(new SkillDropReward(40, 2, Items.EMERALD, new MinMax(2, 5)));
        rareDrops.dropReward(new SkillDropReward(50, 1, Items.ENCHANTED_GOLDEN_APPLE, new MinMax(1, 1)));

        b.skill.dropTables.add(skillDrops.build());
        b.skill.dropTables.add(rareDrops.build());

        b.blockExp(Blocks.WHEAT, 7);
        b.blockExp(Blocks.POTATOES, 7);
        b.blockExp(Blocks.CARROTS, 7);
        b.blockExp(Blocks.BEETROOTS, 7);

        b.regens(10, 2);
        b.regens(20, 4);
        b.regens(30, 6);
        b.regens(40, 8);
        b.regens(50, 10);

        return b.build();
    }
}
