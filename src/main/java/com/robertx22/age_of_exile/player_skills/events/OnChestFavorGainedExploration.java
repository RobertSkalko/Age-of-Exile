package com.robertx22.age_of_exile.player_skills.events;

import com.robertx22.age_of_exile.capability.player.PlayerSkills;
import com.robertx22.age_of_exile.database.data.player_skills.PlayerSkill;
import com.robertx22.age_of_exile.database.registry.Database;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.saveclasses.player_skills.PlayerSkillEnum;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.SkillDropEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class OnChestFavorGainedExploration {

    public static void run(LootInfo info, PlayerEntity player, int favor) {

        PlayerSkill exploration = Database.PlayerSkills()
            .get(PlayerSkillEnum.EXPLORATION.id);

        PlayerSkills skills = Load.playerSkills(player);

        skills.addExp(PlayerSkillEnum.EXPLORATION, favor);
        List<ItemStack> list = exploration.getExtraDropsFor(player, favor, LevelUtils.levelToTier(info.level));

        SkillDropEvent effect = new SkillDropEvent(player, PlayerSkillEnum.EXPLORATION, list);
        effect.Activate();

        effect.extraDrops.forEach(x -> PlayerUtils.giveItem(x, player));

    }
}
