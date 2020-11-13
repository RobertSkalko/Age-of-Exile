package com.robertx22.age_of_exile.aoe_data.database.player_skills.skills;

import com.robertx22.age_of_exile.aoe_data.database.player_skills.PlayerSkillBuilder;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.mmorpg.ModRegistry;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;

public class InscribingAdder {

    public static PlayerSkill createSkill() {

        PlayerSkillBuilder b = PlayerSkillBuilder.of(6, PlayerSkillEnum.INSCRIBING);
        b.addDefaultBonusExpRewards();
        b.addDefaultHpMsMana();

        ModRegistry.INSCRIBING.ALL_TABLETS.forEach(x -> b.itemCraftExp(x, 20));

        b.itemCraftExp(ModRegistry.INSCRIBING.SPAWN_TELEPORT, 2);
        b.itemCraftExp(ModRegistry.INSCRIBING.DEATH_TELEPORT, 2);
        b.itemCraftExp(ModRegistry.INSCRIBING.RANDOM_TELEPORT, 2);

        ModRegistry.INSCRIBING.ENCHANT_SCROLL_MAP.values()
            .forEach(x -> b.itemCraftExp(x, 25 + x.tier.tier * 10));

        return b.build();
    }
}
