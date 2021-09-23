package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class WeaponCraftAdder {
    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(5, PlayerSkillEnum.WEAPON_CRAFTING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        return b.build();
    }

}
