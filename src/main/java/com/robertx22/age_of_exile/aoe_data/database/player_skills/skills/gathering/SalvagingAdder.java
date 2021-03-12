package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.gathering;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class SalvagingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(-1, PlayerSkillEnum.SALVAGING);
        b.addBonusYieldMasteryLevelStats(PlayerSkillEnum.SALVAGING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();
        b.addSalvagingBonusYield();
        b.skill.exp_per_action = 20;

        return b.build();
    }
}
