package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class JewelCraftAdder {
    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(3, PlayerSkillEnum.JEWEL_CRAFTING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        return b.build();
    }
}
