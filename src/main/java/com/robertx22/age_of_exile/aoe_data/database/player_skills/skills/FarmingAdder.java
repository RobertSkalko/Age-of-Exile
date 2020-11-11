package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.vanilla_mc.items.foods.FoodTier;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.FOOD_ITEMS;

public class FarmingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(PlayerSkillEnum.FARMING);
        b.addDefaultBonusExpRewards();

        b.blockExp(Blocks.MELON, 15);
        b.blockExp(Blocks.WHEAT, 5);
        b.blockExp(Blocks.POTATOES, 5);
        b.blockExp(Blocks.CARROTS, 5);
        b.blockExp(Blocks.BEETROOTS, 5);

        b.dropReward(new SkillDropReward(10, 100, FOOD_ITEMS.MAT_TIER_MAP.get(FoodTier.SPIRITUAL), new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(20, 75, FOOD_ITEMS.MAT_TIER_MAP.get(FoodTier.CELESTIAL), new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(30, 50, FOOD_ITEMS.MAT_TIER_MAP.get(FoodTier.EMPYREAN), new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(40, 25, FOOD_ITEMS.MAT_TIER_MAP.get(FoodTier.ANGELIC), new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(50, 10, FOOD_ITEMS.MAT_TIER_MAP.get(FoodTier.DIVINE), new MinMax(1, 1)));

        b.dropReward(new SkillDropReward(5, 10, Items.BONE_MEAL, new MinMax(1, 2)));
        b.dropReward(new SkillDropReward(15, 2, Items.GOLDEN_APPLE, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(25, 1, Items.EMERALD, new MinMax(1, 2)));
        b.dropReward(new SkillDropReward(35, 2, Items.EMERALD, new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(45, 1, Items.ENCHANTED_GOLDEN_APPLE, new MinMax(1, 1)));

        return b.build();
    }
}
