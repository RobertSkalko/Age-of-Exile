package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.data.player_skills.SkillDropReward;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;

public class MiningAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(PlayerSkillEnum.MINING);
        b.addDefaultBonusExpRewards();

        b.blockExp(Blocks.DIAMOND_ORE, 50);
        b.blockExp(Blocks.GOLD_ORE, 15);
        b.blockExp(Blocks.IRON_ORE, 10);
        b.blockExp(Blocks.EMERALD_ORE, 50);
        b.blockExp(Blocks.LAPIS_ORE, 5);
        b.blockExp(Blocks.REDSTONE_ORE, 2);
        b.blockExp(Blocks.COAL_ORE, 1);
        b.blockExp(Blocks.NETHER_GOLD_ORE, 1);
        b.blockExp(Blocks.NETHER_QUARTZ_ORE, 1);

        b.dropReward(new SkillDropReward(4, 1, ModRegistry.TABLETS.BLANK_TABLET, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(9, 2, ModRegistry.TABLETS.BLANK_TABLET, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(14, 3, ModRegistry.TABLETS.BLANK_TABLET, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(19, 3, ModRegistry.TABLETS.BLANK_TABLET, new MinMax(1, 2)));
        b.dropReward(new SkillDropReward(24, 4, ModRegistry.TABLETS.BLANK_TABLET, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(29, 2, ModRegistry.TABLETS.RARE_BLANK_TABLET, new MinMax(1, 1)));

        b.dropReward(new SkillDropReward(5, 10, Items.COAL, new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(10, 10, Items.IRON_NUGGET, new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(15, 10, Items.GOLD_NUGGET, new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(20, 10, Items.ENDER_PEARL, new MinMax(1, 2)));
        b.dropReward(new SkillDropReward(25, 2, Items.DIAMOND, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(30, 2, Items.IRON_INGOT, new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(35, 2, Items.DIAMOND, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(40, 2, Items.GOLD_INGOT, new MinMax(1, 3)));
        b.dropReward(new SkillDropReward(45, 1, Items.NETHERITE_INGOT, new MinMax(1, 1)));
        b.dropReward(new SkillDropReward(50, 2, Items.DIAMOND, new MinMax(1, 3)));

        return b.build();
    }
}