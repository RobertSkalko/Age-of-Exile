package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.TIERED;

public class MiningAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(1, PlayerSkillEnum.MINING);
        b.addBonusYieldMasteryLevelStats(PlayerSkillEnum.MINING);
        b.addDefaultBonusExpRewards();

        b.totalDamage(10, 1);
        b.totalDamage(20, 2);
        b.totalDamage(30, 3);
        b.totalDamage(40, 4);
        b.totalDamage(50, 5);

        b.smeltExp(Items.GOLD_INGOT, 15);
        b.smeltExp(Items.IRON_INGOT, 10);

        b.blockExp(Blocks.DIAMOND_ORE, 50);
        b.blockExp(Blocks.EMERALD_ORE, 50);
        b.blockExp(Blocks.LAPIS_ORE, 5);
        b.blockExp(Blocks.REDSTONE_ORE, 2);
        b.blockExp(Blocks.COAL_ORE, 1);
        b.blockExp(Blocks.NETHER_GOLD_ORE, 1);
        b.blockExp(Blocks.NETHER_QUARTZ_ORE, 1);

        b.addTieredDrops(0.5F, x -> TIERED.STONE_TIER_MAP.get(x));

        /*
        DropRewardsBuilder rareDrops = DropRewardsBuilder.of(0.25F);
        rareDrops.dropReward(new SkillDropReward(5, 10, Items.COAL, new MinMax(1, 3)));
        rareDrops.dropReward(new SkillDropReward(10, 5, Items.IRON_INGOT, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(15, 5, Items.GOLD_INGOT, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(20, 5, Items.ENDER_PEARL, new MinMax(1, 2)));
        rareDrops.dropReward(new SkillDropReward(25, 2, Items.DIAMOND, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(30, 2, Items.IRON_INGOT, new MinMax(1, 3)));
        rareDrops.dropReward(new SkillDropReward(35, 1, Items.DIAMOND, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(40, 1, Items.GOLD_INGOT, new MinMax(1, 3)));
        rareDrops.dropReward(new SkillDropReward(45, 1, Items.NETHERITE_INGOT, new MinMax(1, 1)));
        rareDrops.dropReward(new SkillDropReward(50, 1, Items.DIAMOND, new MinMax(1, 3)));
        b.skill.dropTables.add(rareDrops.build());

         */

        return b.build();
    }
}
