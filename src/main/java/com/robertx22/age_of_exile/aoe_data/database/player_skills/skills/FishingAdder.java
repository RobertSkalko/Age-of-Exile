package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class FishingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(3, PlayerSkillEnum.FISHING);
        b.addDefaultBonusExpRewards();
        b.skill.exp_per_action = 50;
        b.skill.loot_chance_per_action_exp *= 3;

        return b.build();
    }
}
