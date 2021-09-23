package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class InscribingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(2, PlayerSkillEnum.INSCRIBING);
        b.skill.exp_per_action = 25;
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        return b.build();
    }

}
