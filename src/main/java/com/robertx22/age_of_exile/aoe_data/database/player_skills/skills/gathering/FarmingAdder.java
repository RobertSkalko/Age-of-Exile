package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import org.apache.commons.lang3.tuple.ImmutablePair;

import static com.robertx22.age_of_exile.mmorpg.ModRegistry.TIERED;

public class FarmingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(2, PlayerSkillEnum.FARMING);
        b.addBonusYieldMasteryLevelStats(PlayerSkillEnum.FARMING);
        b.addDefaultBonusExpRewards();

        ModRegistry.BLOCKS.FARMING_PLANTS.entrySet()
            .forEach(x -> {
                b.blockExp(x.getValue(), ((x.getKey().tier + 1) * 15));
            });



        b.regens(10, 2);
        b.regens(20, 4);
        b.regens(30, 6);
        b.regens(40, 8);
        b.regens(50, 10);

        return b.build();
    }
}
