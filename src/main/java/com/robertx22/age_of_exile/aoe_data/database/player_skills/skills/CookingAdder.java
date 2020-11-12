package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class CookingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(5, PlayerSkillEnum.COOKING);
        b.addDefaultBonusExpRewards();

        ModRegistry.FOOD_ITEMS.MAP.values()
            .forEach(x -> b.itemCraftExp(x, 20));

        return b.build();
    }
}

