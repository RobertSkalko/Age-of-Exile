package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills.crafting;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.BackpackItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.ProfessionItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.TabletItems;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class BlacksmithingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(8, PlayerSkillEnum.BLACKSMITHING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        ProfessionItems.KEY_TIER_MAP.values()
            .forEach(x -> b.itemCraftExp(x.get(), 25 + x.get().tier.tier * 20));

        BackpackItems.ALL
            .forEach(x -> b.itemCraftExp(x.get(), 100 + x.get().tier.tier * 100));

        b.itemCraftExp(TabletItems.BLANK_TABLET.get(), 20);
        b.itemCraftExp(TabletItems.RARE_BLANK_TABLET.get(), 40);

        return b.build();
    }

}
