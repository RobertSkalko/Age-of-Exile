package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;

public class OnChestFavorGainedExploration {

    public static void run(PlayerEntity player, int favor) {

        PlayerSkill exploration = Database.PlayerSkills()
            .get(PlayerSkillEnum.EXPLORATION.id);

        PlayerSkills skills = Load.playerSkills(player);

        skills.addExp(PlayerSkillEnum.EXPLORATION, favor);
        exploration.getExtraDropsFor(skills, favor)
            .forEach(x -> PlayerUtils.giveItem(x, player));

    }
}
