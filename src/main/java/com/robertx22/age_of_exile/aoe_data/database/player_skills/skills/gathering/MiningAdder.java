package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.player_skills.items.fishing.LureType;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.ImmutablePair;

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
        b.blockExp(Blocks.LAPIS_ORE, 10);
        b.blockExp(Blocks.REDSTONE_ORE, 2);
        b.blockExp(Blocks.COAL_ORE, 2);
        b.blockExp(Blocks.NETHER_GOLD_ORE, 3);
        b.blockExp(Blocks.NETHER_QUARTZ_ORE, 3);

        b.addTieredDrops(1F, x -> TIERED.STONE_TIER_MAP.get(x));

        b.addTieredDrops(0.1F, tier -> {
            return TIERED.LURES.get(ImmutablePair.of(LureType.INK, tier));
        });

        return b.build();
    }
}
