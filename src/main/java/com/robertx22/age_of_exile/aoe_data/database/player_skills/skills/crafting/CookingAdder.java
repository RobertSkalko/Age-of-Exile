package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.FoodItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class CookingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(6, PlayerSkillEnum.COOKING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        FoodItems.MAP.values()
            .forEach(x -> b.itemCraftExp(x.get(), 40 + x.get().tier.tier * 30));

        return b.build();
    }

}

