package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class TinkeringAdder {
    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(8, PlayerSkillEnum.TINKERING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        ModRegistry.TINKERING.KEY_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 100 + x.tier.tier * 50));

        ModRegistry.TINKERING.LEATHER_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 25 + x.tier.tier * 15));

        ModRegistry.TINKERING.MAT_BACKPACKS_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 100 + x.tier.tier * 50));
        ModRegistry.TINKERING.VALUABLES_BACKPACKS_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 100 + x.tier.tier * 50));
        ModRegistry.TINKERING.NORMAL_BACKPACKS_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 100 + x.tier.tier * 50));

        return b.build();
    }
}
