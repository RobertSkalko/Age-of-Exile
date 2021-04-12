package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class BlacksmithingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(8, PlayerSkillEnum.BLACKSMITHING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        ModRegistry.TIERED.KEY_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 25 + x.tier.tier * 20));

        ModRegistry.TIERED.TOOL_UPGRADE.values()
            .forEach(x -> b.itemCraftExp(x, 20 + x.tier.tier * 10));

        ModRegistry.BACKPACK_UPGRADES.ALL
            .forEach(x -> b.itemCraftExp(x, 100 + x.tier.tier * 100));

        b.itemCraftExp(ModRegistry.INSCRIBING.BLANK_TABLET, 20);
        b.itemCraftExp(ModRegistry.INSCRIBING.RARE_BLANK_TABLET, 40);

        return b.build();
    }

}
